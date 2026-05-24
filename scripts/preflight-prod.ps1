param(
    [string]$EnvFile = ".env",
    [string]$ComposeFile = "docker-compose.yml"
)

$ErrorActionPreference = "Stop"

function Write-Ok([string]$Message) {
    Write-Host "[OK] $Message" -ForegroundColor Green
}

function Write-Warn([string]$Message) {
    Write-Host "[WARN] $Message" -ForegroundColor Yellow
}

function Write-Fail([string]$Message) {
    Write-Host "[FAIL] $Message" -ForegroundColor Red
}

function Import-DotEnv([string]$Path) {
    if (-not (Test-Path -LiteralPath $Path)) {
        throw "Env file '$Path' not found."
    }

    $map = @{}
    foreach ($line in Get-Content -LiteralPath $Path) {
        $trimmed = $line.Trim()
        if ([string]::IsNullOrWhiteSpace($trimmed) -or $trimmed.StartsWith("#")) {
            continue
        }

        $parts = $trimmed -split "=", 2
        if ($parts.Count -ne 2) {
            continue
        }

        $key = $parts[0].Trim()
        $value = $parts[1].Trim()
        $map[$key] = $value
    }

    return $map
}

function Test-PortFree([int]$Port) {
    $listener = $null
    try {
        $listener = [System.Net.Sockets.TcpListener]::new([System.Net.IPAddress]::Loopback, $Port)
        $listener.Start()
        return $true
    } catch {
        return $false
    } finally {
        if ($null -ne $listener) {
            $listener.Stop()
        }
    }
}

Write-Host "== Production preflight ==" -ForegroundColor Cyan
Write-Host "Env file: $EnvFile"
Write-Host "Compose file: $ComposeFile"

$envMap = Import-DotEnv -Path $EnvFile

$requiredVars = @(
    "DB_PASSWORD",
    "TOKEN_SIGNING_KEY",
    "CORS_ALLOWED_ORIGINS",
    "POSTGRES_EXTERNAL_PORT",
    "REDIS_EXTERNAL_PORT",
    "EUREKA_EXTERNAL_PORT",
    "USERS_SERVICE_EXTERNAL_PORT",
    "METHODOLOGIES_SERVICE_EXTERNAL_PORT",
    "TESTS_SERVICE_EXTERNAL_PORT",
    "ATTEMPTS_SERVICE_EXTERNAL_PORT",
    "NGINX_EXTERNAL_PORT"
)

$failed = $false

foreach ($varName in $requiredVars) {
    if (-not $envMap.ContainsKey($varName) -or [string]::IsNullOrWhiteSpace($envMap[$varName])) {
        Write-Fail "Required variable '$varName' is missing or empty."
        $failed = $true
    } else {
        Write-Ok "Required variable '$varName' is set."
    }
}

if ($envMap.ContainsKey("DB_PASSWORD") -and $envMap["DB_PASSWORD"] -eq "change_me") {
    Write-Fail "DB_PASSWORD still uses placeholder 'change_me'."
    $failed = $true
}

if ($envMap.ContainsKey("TOKEN_SIGNING_KEY") -and $envMap["TOKEN_SIGNING_KEY"] -eq "replace_with_base64_secret_key") {
    Write-Fail "TOKEN_SIGNING_KEY still uses placeholder value."
    $failed = $true
}

if ($envMap.ContainsKey("CORS_ALLOWED_ORIGINS") -and $envMap["CORS_ALLOWED_ORIGINS"] -match "localhost") {
    Write-Warn "CORS_ALLOWED_ORIGINS still contains localhost. Verify production frontend domain is configured."
}

$portVarNames = @(
    "POSTGRES_EXTERNAL_PORT",
    "REDIS_EXTERNAL_PORT",
    "EUREKA_EXTERNAL_PORT",
    "USERS_SERVICE_EXTERNAL_PORT",
    "METHODOLOGIES_SERVICE_EXTERNAL_PORT",
    "TESTS_SERVICE_EXTERNAL_PORT",
    "ATTEMPTS_SERVICE_EXTERNAL_PORT",
    "NGINX_EXTERNAL_PORT"
)

$seenPorts = @{}
foreach ($portVar in $portVarNames) {
    if (-not $envMap.ContainsKey($portVar)) {
        continue
    }

    $value = $envMap[$portVar]
    $parsedPort = 0
    if (-not [int]::TryParse($value, [ref]$parsedPort)) {
        Write-Fail "$portVar='$value' is not a valid integer port."
        $failed = $true
        continue
    }

    $port = $parsedPort
    if ($port -lt 1 -or $port -gt 65535) {
        Write-Fail "$portVar=$port is outside the valid TCP range."
        $failed = $true
        continue
    }

    if ($seenPorts.ContainsKey($port)) {
        Write-Fail "Duplicate external port $port in '$portVar' and '$($seenPorts[$port])'."
        $failed = $true
        continue
    }
    $seenPorts[$port] = $portVar

    if (Test-PortFree -Port $port) {
        Write-Ok "$portVar=$port is currently free on host."
    } else {
        Write-Warn "$portVar=$port is already in use on host. Compose may fail to start."
    }
}

if (-not (Test-Path -LiteralPath $ComposeFile)) {
    Write-Fail "Compose file '$ComposeFile' not found."
    $failed = $true
} else {
    Write-Ok "Compose file '$ComposeFile' exists."
}

if ($failed) {
    throw "Preflight checks failed. Fix errors above before production start."
}

Write-Host "Preflight completed successfully." -ForegroundColor Green
