@echo off
setlocal EnableExtensions
cd /d "%~dp0"

powershell.exe -NoLogo -NoProfile -ExecutionPolicy Bypass -File "%~dp0run-pm1-emulator-test.ps1"
set "PM1_EXIT_CODE=%ERRORLEVEL%"

if not "%PM1_EXIT_CODE%"=="0" (
    echo.
    echo PM-1 test launcher failed with exit code %PM1_EXIT_CODE%.
    echo Review the error above, then press any key to close this window.
    pause >nul
)

exit /b %PM1_EXIT_CODE%
