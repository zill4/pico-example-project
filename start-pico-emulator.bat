@echo off
REM Boots the "Pico" AVD with PICO's own emulator build (6.0).
REM Android Studio's stock SDK emulator cannot boot this AVD: the PICO system
REM image and kernel live under %LOCALAPPDATA%\PICO\sdk\6.0\emulator\system-images,
REM which only PICO's emulator finds (via ANDROID_SDK_ROOT below).
REM Start this first, wait for the device to come online, then press Run in
REM Android Studio and deploy to the running device (emulator-5554).
set "ANDROID_SDK_ROOT=%LOCALAPPDATA%\PICO\sdk\6.0\emulator\system-images"
set "ANDROID_AVD_HOME=%USERPROFILE%\.android\avd"
set "ANGLE_DEFAULT_PLATFORM=vulkan"
start "PICO Emulator" /D "%LOCALAPPDATA%\PICO\sdk\6.0\emulator" "%LOCALAPPDATA%\PICO\sdk\6.0\emulator\emulator.exe" -avd Pico -gpu angle_indirect -allow-host-audio -writable-system
echo PICO emulator starting. It appears in adb / Android Studio as emulator-5554.
