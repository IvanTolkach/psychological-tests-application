param(
    [string]$EnvFile = ".env",
    [string]$TargetHost = "localhost",
    [int]$Retries = 20,
    [int]$DelaySeconds = 3
)

$ErrorActionPreference = "Stop"

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

        $map[$parts[0].Trim()] = $parts[1].Trim()
    }
    return $map
}

function Invoke-WithRetry {
    param(
        [scriptblock]$Action,
        [string]$Name
    )

    for ($i = 1; $i -le $Retries; $i++) {
        try {
            & $Action
            Write-Host "[OK] $Name" -ForegroundColor Green
            return
        } catch {
            if ($i -eq $Retries) {
                throw "[FAIL] ${Name}: $($_.Exception.Message)"
            }
            Write-Host "[RETRY $i/$Retries] ${Name}: $($_.Exception.Message)" -ForegroundColor Yellow
            Start-Sleep -Seconds $DelaySeconds
        }
    }
}

function Assert-Health200([string]$Url, [string]$Name) {
    Invoke-WithRetry -Name $Name -Action {
        $response = Invoke-WebRequest -Uri $Url -Method GET -TimeoutSec 10 -UseBasicParsing
        if ($response.StatusCode -ne 200) {
            throw "Expected 200, got $($response.StatusCode)"
        }
    }
}

function Assert-Not5xx([string]$Url, [string]$Name) {
    Invoke-WithRetry -Name $Name -Action {
        try {
            $response = Invoke-WebRequest -Uri $Url -Method GET -TimeoutSec 10 -UseBasicParsing
            $statusCode = [int]$response.StatusCode
        } catch {
            if ($_.Exception.Response) {
                $statusCode = [int]$_.Exception.Response.StatusCode.value__
            } else {
                throw
            }
        }

        if ($statusCode -ge 500) {
            throw "Expected non-5xx, got $statusCode"
        }
    }
}

Write-Host "== Production smoke test ==" -ForegroundColor Cyan
Write-Host "Host: $TargetHost"
Write-Host "Env file: $EnvFile"

$envMap = Import-DotEnv -Path $EnvFile

$requiredPorts = @(
    "EUREKA_EXTERNAL_PORT",
    "USERS_SERVICE_EXTERNAL_PORT",
    "METHODOLOGIES_SERVICE_EXTERNAL_PORT",
    "TESTS_SERVICE_EXTERNAL_PORT",
    "ATTEMPTS_SERVICE_EXTERNAL_PORT",
    "NGINX_EXTERNAL_PORT"
)

foreach ($portVar in $requiredPorts) {
    if (-not $envMap.ContainsKey($portVar) -or [string]::IsNullOrWhiteSpace($envMap[$portVar])) {
        throw "Missing required port variable '$portVar' in $EnvFile."
    }
}

$eurekaPort = [int]$envMap["EUREKA_EXTERNAL_PORT"]
$usersPort = [int]$envMap["USERS_SERVICE_EXTERNAL_PORT"]
$methodologiesPort = [int]$envMap["METHODOLOGIES_SERVICE_EXTERNAL_PORT"]
$testsPort = [int]$envMap["TESTS_SERVICE_EXTERNAL_PORT"]
$attemptsPort = [int]$envMap["ATTEMPTS_SERVICE_EXTERNAL_PORT"]
$nginxPort = [int]$envMap["NGINX_EXTERNAL_PORT"]

Assert-Health200 -Url "http://${TargetHost}:$eurekaPort/actuator/health" -Name "eureka health"
Assert-Health200 -Url "http://${TargetHost}:$usersPort/actuator/health" -Name "users health"
Assert-Health200 -Url "http://${TargetHost}:$methodologiesPort/actuator/health" -Name "methodologies health"
Assert-Health200 -Url "http://${TargetHost}:$testsPort/actuator/health" -Name "tests health"
Assert-Health200 -Url "http://${TargetHost}:$attemptsPort/actuator/health" -Name "attempts health"

Assert-Not5xx -Url "http://${TargetHost}:$nginxPort/eureka/" -Name "nginx -> eureka route"
Assert-Not5xx -Url "http://${TargetHost}:$nginxPort/api/tests" -Name "nginx -> tests route"
Assert-Not5xx -Url "http://${TargetHost}:$nginxPort/api/test-attempts" -Name "nginx -> attempts route"
Assert-Not5xx -Url "http://${TargetHost}:$nginxPort/api/methodologies" -Name "nginx -> methodologies route"
Assert-Not5xx -Url "http://${TargetHost}:$nginxPort/api/students" -Name "nginx -> users route"

Write-Host "Smoke checks passed." -ForegroundColor Green
