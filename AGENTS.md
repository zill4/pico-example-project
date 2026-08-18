# AGENTS.md

These instructions apply to the entire repository.

## Start here

Before changing the project:

1. Read `README.md` and `PICO_DEVELOPMENT.md`.
2. Inspect the files relevant to the requested feature and preserve existing user changes.
3. Check the current PICO/toolchain constraints against official sources when they may have changed.
4. State what will be validated, then perform the smallest relevant verification after editing.

`PICO_DEVELOPMENT.md` is the project memory. Keep it useful, factual, and current.

## Maintain the running notes

Update `PICO_DEVELOPMENT.md` whenever work does one or more of the following:

- completes or changes a setup/curriculum item;
- changes project architecture, dependencies, identifiers, tooling, or supported devices;
- introduces a meaningful product or technical decision;
- produces a useful experiment result, limitation, workaround, or unresolved issue;
- changes the immediate next action.

Use `YYYY-MM-DD` dates. Add a concise work-session entry with outcome, validation, and next step. Add durable choices to the decision log with their reason and consequence. Never mark an install, account, device, build, test, or runtime behavior as verified without direct evidence. Never record credentials, tokens, private keys, or other secrets.

## Project baseline

- Native PICO OS 6 spatial application built with Kotlin and Jetpack Compose.
- Spatial SDK BOM: `6.0.0`.
- Android Studio baseline: `2025.1.4`; PICO currently documents support for the `2025.1.x` family.
- Android API: compile/target 35, minimum 26.
- Build stack: Gradle `8.10.2`, Android Gradle Plugin `8.8.0`, Kotlin `2.0.0`.
- Modules: `app` for application code and `editor-asset` for Spatial Editor content.
- App entry and container registration: `app/src/main/java/com/pico/spatial/sample/welcomespace/Main.kt`.
- Planar UI/navigation: `app/src/main/java/com/pico/spatial/sample/welcomespace/ui`.
- Immersive Stage: `app/src/main/java/com/pico/spatial/sample/welcomespace/ui/room`.
- Volumetric inspection: `app/src/main/java/com/pico/spatial/sample/welcomespace/ui/display`.
- 3D source assets: `editor-asset/src/main/res3d/WelcomeSpace`.

Preserve the existing separation between planar Compose UI, volumetric content, Full Space Stage logic, data/ViewModels, ECS systems, and editor-owned 3D assets unless a documented decision changes it. Register spatial containers through `mainApp`. Keep generated build output out of source edits.

## Local and portable configuration

`local.properties`, Android SDK paths, PICO SDK paths, AVD state, account state, and IDE user plugins are machine-local. Do not hard-code those paths into application source or portable build configuration, and do not commit `local.properties`.

On the currently inventoried Windows workstation, Java is available through Android Studio's bundled JBR but is not on the shell `PATH`. A PowerShell build can use:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
.\gradlew.bat :app:assembleDebug
```

Use `start-pico-emulator.bat` for the local `Pico` AVD. It intentionally uses PICO's emulator distribution and system images. Use the Android SDK's `platform-tools/adb.exe` directly if `adb` is not on `PATH`.

Do not claim the PICO Intelligent Plugins are active just because files or `pico-cli` exist. Verify PICO-specific skills/tools in the current agent session. If unavailable, say so and use official PICO documentation and repository inspection; do not invent tool names or outputs. Plugin setup requires a fresh agent session before verification.

## Implementation conventions

- Match existing Kotlin and Compose patterns and package structure.
- Keep UI state in ViewModels where the surrounding feature does so; avoid embedding domain state in composables.
- Reuse the existing repository/catalog, navigation, dependency-injection, and ECS patterns before adding new abstractions.
- Treat Stage and WindowContainer lifecycle cleanup as part of feature correctness.
- Keep `.bundle` and `.ktx` uncompressed as configured unless PICO documentation explicitly requires a change.
- Do not manually edit generated `build` directories or generated `editor-asset.bundle` output.
- Preserve licenses and attribution for imported code, models, textures, and audio. Record asset provenance and usage terms in the running notes before shipping them.
- Prefer official PICO documentation and PICO-owned repositories for SDK behavior. Record the source link and review date when making a version-sensitive decision.

## Validation

Choose validation proportional to the change, starting narrow and expanding as needed:

```powershell
.\gradlew.bat spotlessCheck
.\gradlew.bat test
.\gradlew.bat :app:assembleDebug
```

- Kotlin/data/ViewModel changes: run focused unit tests when present, then `test`.
- Compose/navigation/container changes: build and exercise the affected flow in the PICO Emulator.
- Spatial Editor/3D asset changes: rebuild the asset module/app and inspect the scene in Spatial Editor and the emulator.
- Stage, tracking, sense, audio, or device-specific changes: test lifecycle transitions and document whether validation used the emulator or physical hardware.
- Before handoff, report exactly what ran, what passed, and what remains unverified.

If a command cannot run because of missing local tooling or account access, record the concrete blocker and the best next action in `PICO_DEVELOPMENT.md`; do not mark the curriculum item complete.

<!-- pico-cli:plugin-context:pico-spatial-agentic-tools:start -->
## Plugin Context

Also read `./PICO-SPATIAL-AGENTIC-TOOLS.AGENTS.md` for PICO Spatial plugin guidance.
<!-- pico-cli:plugin-context:pico-spatial-agentic-tools:end -->
