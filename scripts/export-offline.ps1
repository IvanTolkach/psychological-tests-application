param(
    [string]$ProjectName = "pta",
    [string]$EnvFile = ".env",
    [string]$ComposeFile = "docker-compose.yml",
    [string]$OutputDir = "",
    [switch]$SkipPull,
    [switch]$LeavePostgresStopped
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

Write-Host "== Export offline bundle ==" -ForegroundColor Cyan
Write-Host "Project: $ProjectName"
Write-Host "Env file: $EnvFile"
Write-Host "Compose file: $ComposeFile"

Require-Path -Path $EnvFile -Label "Env file"
Require-Path -Path $ComposeFile -Label "Compose file"

$repoRoot = (Get-Location).Path

if ([string]::IsNullOrWhiteSpace($OutputDir)) {
    $OutputDir = Join-Path $repoRoot ("offline-bundle-" + (Get-Date -Format "yyyyMMdd-HHmmss"))
}

if (-not (Test-Path -LiteralPath $OutputDir)) {
    New-Item -ItemType Directory -Path $OutputDir | Out-Null
}

$outputDirResolved = (Resolve-Path -LiteralPath $OutputDir).Path
$imagesTar = Join-Path $outputDirResolved "images.tar"
$postgresDataTgz = Join-Path $outputDirResolved "postgres_data.tgz"
$appZip = Join-Path $outputDirResolved "app.zip"
$metaJson = Join-Path $outputDirResolved "bundle-meta.json"

if (-not $SkipPull) {
    Write-Info "Pulling service images from compose"
    & docker compose -p $ProjectName --env-file $EnvFile -f $ComposeFile pull
    Write-Ok "Images pulled"
} else {
    Write-Info "Skipping pull (--SkipPull)"
}

Write-Info "Resolving image list from compose"
$images = & docker compose -p $ProjectName --env-file $EnvFile -f $ComposeFile config --images |
    Where-Object { $_ -and $_.Trim() -ne "" -and $_ -notmatch "^WARNING:" } |
    Sort-Object -Unique

if (-not $images -or $images.Count -eq 0) {
    throw "Could not resolve compose images."
}

Write-Info "Saving images to $imagesTar"
& docker save -o $imagesTar @images
Write-Ok "Saved Docker images"

Write-Info "Detecting postgres volume for project '$ProjectName'"
$pgVolume = & docker volume ls `
    --filter "label=com.docker.compose.project=$ProjectName" `
    --filter "label=com.docker.compose.volume=postgres_data" `
    --format "{{.Name}}" |
    Select-Object -First 1

if (-not $pgVolume) {
    $candidates = @(& docker volume ls --filter "label=com.docker.compose.volume=postgres_data" --format "{{.Name}}" | Where-Object { $_ -and $_.Trim() -ne "" })
    if ($candidates.Count -eq 1) {
        $pgVolume = $candidates[0]
        $detectedProject = (& docker volume inspect --format '{{ index .Labels \"com.docker.compose.project\" }}' $pgVolume)
        if ($detectedProject) { $detectedProject = $detectedProject.Trim() }
        if ($detectedProject) {
            Write-Host "[WARN] Project '$ProjectName' not found. Auto-detected project '$detectedProject' from volume labels." -ForegroundColor Yellow
            $ProjectName = $detectedProject
        }
    } elseif ($candidates.Count -gt 1) {
        Write-Host "Found postgres_data volumes in Docker:" -ForegroundColor Yellow
        $candidates | ForEach-Object { Write-Host "  - $_" -ForegroundColor Yellow }
        throw "Multiple postgres_data volumes found. Rerun with -ProjectName <name>."
    }
}

if (-not $pgVolume) {
    throw "Could not find postgres_data volume. Start the stack first, then rerun."
}

Write-Ok "Using postgres volume: $pgVolume"

Write-Info "Stopping postgres for consistent data copy"
& docker compose -p $ProjectName --env-file $EnvFile -f $ComposeFile stop postgres

Write-Info "Archiving postgres volume to $postgresDataTgz"
& docker run --rm `
    -v "${pgVolume}:/from:ro" `
    -v "${outputDirResolved}:/to" `
    postgres:18 `
    sh -c "cd /from && tar czf /to/postgres_data.tgz ."

Write-Ok "Postgres data archived"

if (-not $LeavePostgresStopped) {
    Write-Info "Starting postgres back"
    & docker compose -p $ProjectName --env-file $EnvFile -f $ComposeFile up -d postgres
    Write-Ok "Postgres started"
} else {
    Write-Info "Leaving postgres stopped (--LeavePostgresStopped)"
}

Write-Info "Packing app files to $appZip"
$packItems = @(
    "docker-compose.yml",
    ".env",
    "nginx",
    "scripts",
    "backups"
) | Where-Object { Test-Path -LiteralPath $_ }

if (-not $packItems -or $packItems.Count -eq 0) {
    throw "No files found to create app.zip."
}

Compress-Archive -LiteralPath $packItems -DestinationPath $appZip -Force
Write-Ok "App package created"

$meta = @{
    created_at = (Get-Date).ToString("s")
    project_name = $ProjectName
    compose_file = $ComposeFile
    env_file = $EnvFile
    postgres_volume = $pgVolume
    files = @(
        "images.tar",
        "postgres_data.tgz",
        "app.zip"
    )
}
$meta | ConvertTo-Json | Set-Content -LiteralPath $metaJson

Write-Host ""
Write-Host "Bundle ready: $outputDirResolved" -ForegroundColor Green
Write-Host "Contains: images.tar, postgres_data.tgz, app.zip, bundle-meta.json"
