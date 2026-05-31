param(
    [string]$BundleDir = ".",
    [string]$DeployDir = "C:\deploy\pta",
    [string]$ProjectName = "pta",
    [switch]$ReplaceExistingVolume,
    [switch]$SkipSmoke
)

$ErrorActionPreference = "Stop"

function Write-Info([string]$Message) {
    Write-Host "[INFO] $Message" -ForegroundColor Cyan
}

function Write-Ok([string]$Message) {
    Write-Host "[OK] $Message" -ForegroundColor Green
}

function Require-Path([string]$Path, [string]$Label) {
    if (-not (Test-Path -LiteralPath $Path)) {
        throw "$Label not found: $Path"
    }
}

Write-Host "== Import offline bundle ==" -ForegroundColor Cyan
Write-Host "Bundle dir: $BundleDir"
Write-Host "Deploy dir: $DeployDir"
Write-Host "Project: $ProjectName"

Require-Path -Path $BundleDir -Label "Bundle directory"

$bundleDirResolved = (Resolve-Path -LiteralPath $BundleDir).Path
$imagesTar = Join-Path $bundleDirResolved "images.tar"
$postgresDataTgz = Join-Path $bundleDirResolved "postgres_data.tgz"
$appZip = Join-Path $bundleDirResolved "app.zip"

Require-Path -Path $imagesTar -Label "images.tar"
Require-Path -Path $postgresDataTgz -Label "postgres_data.tgz"
Require-Path -Path $appZip -Label "app.zip"

if (-not (Test-Path -LiteralPath $DeployDir)) {
    New-Item -ItemType Directory -Path $DeployDir | Out-Null
}

$deployDirResolved = (Resolve-Path -LiteralPath $DeployDir).Path

Write-Info "Extracting app.zip to deploy directory"
Expand-Archive -LiteralPath $appZip -DestinationPath $deployDirResolved -Force
Write-Ok "App files extracted"

Set-Location -LiteralPath $deployDirResolved

$composeFile = "docker-compose.yml"
$envFile = ".env"

Require-Path -Path $composeFile -Label "Compose file"
Require-Path -Path $envFile -Label "Env file"

Write-Info "Loading Docker images from tar"
& docker load -i $imagesTar
Write-Ok "Images loaded"

$targetVolume = "${ProjectName}_postgres_data"
Write-Info "Stopping existing stack (if running)"
& docker compose -p $ProjectName --env-file $envFile -f $composeFile down

$existingVolumes = & docker volume ls --format "{{.Name}}"
$volumeExists = $existingVolumes -contains $targetVolume

if ($volumeExists -and (-not $ReplaceExistingVolume)) {
    throw "Volume '$targetVolume' already exists. Rerun with -ReplaceExistingVolume to overwrite it."
}

if ($volumeExists -and $ReplaceExistingVolume) {
    Write-Info "Removing existing volume: $targetVolume"
    & docker volume rm $targetVolume
}

Write-Info "Creating target volume: $targetVolume"
& docker volume create $targetVolume | Out-Null
Write-Ok "Target volume ready"

Write-Info "Restoring PostgreSQL data into volume"
& docker run --rm `
    -v "${targetVolume}:/to" `
    -v "${bundleDirResolved}:/backup" `
    postgres:18 `
    sh -c "cd /to && tar xzf /backup/postgres_data.tgz"
Write-Ok "PostgreSQL data restored"

if (Test-Path -LiteralPath ".\scripts\preflight-prod.ps1") {
    Write-Info "Running preflight checks"
    & .\scripts\preflight-prod.ps1 -EnvFile $envFile -ComposeFile $composeFile
    Write-Ok "Preflight checks passed"
}

Write-Info "Starting stack without pull"
& docker compose -p $ProjectName --env-file $envFile -f $composeFile up -d --pull never
Write-Ok "Stack started"

if ((-not $SkipSmoke) -and (Test-Path -LiteralPath ".\scripts\smoke-prod.ps1")) {
    Write-Info "Running smoke checks"
    & .\scripts\smoke-prod.ps1 -EnvFile $envFile -TargetHost localhost
    Write-Ok "Smoke checks passed"
}

Write-Host ""
Write-Host "Done. Access API gateway on: http://<vm-ip>:8080" -ForegroundColor Green
Write-Host "If your .env uses another NGINX_EXTERNAL_PORT, use that port."
