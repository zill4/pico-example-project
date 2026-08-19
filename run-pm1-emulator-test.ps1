param(
    [switch] $SkipBuild
)

$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot

$deviceId = 'emulator-5554'
$avdName = 'Pico_MVP'
$packageName = 'com.pico.spatial.sample.welcomespace'
$launchActivity = '.platform.LaunchActivity'
$apkPath = Join-Path $PSScriptRoot 'app\build\outputs\apk\debug\app-debug.apk'
$platformAdb = Join-Path $env:LOCALAPPDATA 'Android\Sdk\platform-tools\adb.exe'
$androidPlatformToolsPath = Split-Path -Parent $platformAdb
$spatialEditorPath = Join-Path $env:LOCALAPPDATA 'PICO\sdk\6.0\editor\SpatialEditor'
$captureDirectory = Join-Path $PSScriptRoot 'captures'
$timestamp = Get-Date -Format 'yyyyMMdd-HHmmss'
$logPath = Join-Path $captureDirectory "pm1-stage-lifecycle-$timestamp.log"
$visibleLogPattern = 'SpatialPack_SpatialContainer|LifeCycle|RoomScene'

function Invoke-CheckedCommand {
    param(
        [Parameter(Mandatory)]
        [string] $Command,

        [Parameter(Mandatory)]
        [string[]] $Arguments
    )

    & $Command @Arguments
    if ($LASTEXITCODE -ne 0) {
        throw "Command failed with exit code $LASTEXITCODE`: $Command $($Arguments -join ' ')"
    }
}

if (-not (Get-Command pico-cli -ErrorAction SilentlyContinue)) {
    throw 'pico-cli is not available on PATH.'
}
if (-not (Test-Path -LiteralPath $platformAdb -PathType Leaf)) {
    throw "Android platform-tools adb was not found at $platformAdb"
}
if (-not (Test-Path -LiteralPath $spatialEditorPath -PathType Container)) {
    throw "PICO Spatial Editor was not found at $spatialEditorPath"
}

$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
$env:PATH = "$spatialEditorPath;$androidPlatformToolsPath;$env:PATH"
$env:PICO_CLI_DEVICE = $deviceId

if ($SkipBuild) {
    Write-Host '[1/5] Reusing the existing debug APK for launcher verification...'
} else {
    Write-Host '[1/5] Building and testing the current project...'
    Invoke-CheckedCommand -Command (Join-Path $PSScriptRoot 'gradlew.bat') -Arguments @(
        'spotlessCheck',
        'test',
        ':app:assembleDebug',
        '--no-daemon'
    )
}

if (-not (Test-Path -LiteralPath $apkPath -PathType Leaf)) {
    throw "The debug APK was not created at $apkPath"
}

Write-Host '[2/5] Starting the managed PICO Emulator...'
Invoke-CheckedCommand -Command 'pico-cli' -Arguments @(
    'emulator',
    'start',
    '--avd',
    $avdName,
    '--wait-timeout',
    '180',
    '-y'
)

Write-Host '[3/5] Installing the verified debug APK...'
Invoke-CheckedCommand -Command 'pico-cli' -Arguments @('app', 'install', $apkPath)

Write-Host '[4/5] Launching Welcome Space...'
Invoke-CheckedCommand -Command 'pico-cli' -Arguments @(
    'app',
    'launch',
    $packageName,
    '--activity',
    $launchActivity
)

$appProcessId = ''
for ($attempt = 1; $attempt -le 20 -and -not $appProcessId; $attempt++) {
    $processIdOutput = & $platformAdb -s $deviceId shell pidof $packageName 2>$null
    $appProcessId = "$processIdOutput".Trim()
    if (-not $appProcessId) {
        Start-Sleep -Seconds 1
    }
}
if (-not $appProcessId) {
    throw "Welcome Space did not expose a process ID on $deviceId."
}

New-Item -ItemType Directory -Force -Path $captureDirectory | Out-Null
"Logger requested before Enter Room at $(Get-Date -Format o)" |
    Set-Content -LiteralPath $logPath

Write-Host '[5/5] Attaching the Stage lifecycle logger...'
Write-Host "Full log: $logPath"
Write-Host 'Waiting for the first device log line before declaring readiness...'

$readyPrinted = $false
$logcatArguments = @(
    'app',
    'logcat',
    '--device',
    $deviceId,
    '--pid',
    $appProcessId,
    '--lines',
    '50',
    '--level',
    'I',
    '--follow'
)

& pico-cli @logcatArguments 2>&1 |
    ForEach-Object {
        $line = "$_"
        Add-Content -LiteralPath $logPath -Value $line

        if (-not $readyPrinted) {
            Write-Host ''
            Write-Host '============================================================' -ForegroundColor Green
            Write-Host 'READY - CLICK ENTER ROOM NOW' -ForegroundColor Green
            Write-Host '1. Place an item and confirm its card says In room.' -ForegroundColor Green
            Write-Host '2. MINIMIZE (-) the Decorate Space panel; do not CLOSE (X) it.' -ForegroundColor Green
            Write-Host '3. In Eye Gesture Mode, aim at the placed object and left-click.' -ForegroundColor Green
            Write-Host '4. Look here for PM-1 tap [object_id]: APPLIED.' -ForegroundColor Green
            Write-Host 'Minimizing must leave the app room visible and interactive.' -ForegroundColor Green
            Write-Host 'When finished, return here and press Ctrl+C.' -ForegroundColor Green
            Write-Host '============================================================' -ForegroundColor Green
            Write-Host ''
            $readyPrinted = $true
        }

        if ($line -match $visibleLogPattern) {
            Write-Host $line
        }
    }
