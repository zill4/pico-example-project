# PICO Development: Running Notes + Curriculum

Last updated: 2026-08-18  
Online references last reviewed: 2026-08-18  
Project: Welcome Space `0.13.3` starting point for PICO OS 6 development

## How to use this document

This is the durable project memory for environment setup, learning progress, implementation decisions, experiments, and unresolved questions. Update it whenever development changes the project state or teaches us something worth preserving.

Status markers:

- `[x]` verified complete
- `[~]` partially complete or needs reconfirmation
- `[ ]` not started
- `[!]` blocked or needs a decision

When adding a work-session entry, include the date, outcome, evidence or validation performed, decisions made, and the next useful action. Do not mark a device, account, install, build, or runtime behavior as verified without direct evidence.

## Current focus

Establish a reproducible PICO OS 6 baseline and execute Project 01 from Spatial Language Quest through the complete Chore Helper, using independently validated product markers in `PROJECT_01_ROOM_QUEST.md`.

Immediate next actions:

1. Complete `PM-1` direct selection evidence with a manual PICO Emulator click or physical controller: select and deselect three placed models and capture the `RoomScene` logs and `Selected` labels.
2. Reconfirm reset and Stage exit after the direct select/deselect run, then mark `PM-1` complete only if every checkpoint has evidence.
3. Draft and review Japanese content records for the five stable IDs, including pronunciation provenance, without beginning `PM-2` implementation yet.
4. Sign in to PICO Developer Center if needed and record only whether access works; never record credentials.

## Verified local baseline

Snapshot captured on 2026-08-18. Paths and host details in this section are local observations, not portable project configuration.

### Workstation

- [x] Windows 11 Pro, 64-bit
- [x] AMD Ryzen 9 3900X
- [x] 47.9 GB usable memory
- [x] NVIDIA GeForce RTX 3090
- [x] 321.6 GB free on `C:` at time of inspection
- [x] Host exceeds PICO's documented Windows minimums of Windows 10/11 64-bit, 16 GB memory, 40 GB free disk, Intel Core i5-class CPU, and GTX 1060-class GPU.

### Android and PICO tools

- [x] Android Studio `2025.1.4` is installed at `C:\Program Files\Android\Android Studio`.
- [x] A fresh **Sync Project with Gradle Files** operation completed in Android Studio `2025.1.4` without an unresolved error; the IDE only recommended an optional Android Gradle Plugin upgrade.
- [x] Android Studio's bundled JBR is OpenJDK `21.0.8`.
- [x] Android SDK is configured in local-only `local.properties`.
- [x] Android SDK Platform 35 is installed.
- [x] Sources for Android 35 are installed.
- [x] PICO Developer Center `1.4.10` is installed.
- [~] PICO Developer Center account creation/sign-in cannot be verified from project files; confirm interactively.
- [x] PICO Spatial Plugin exists in the Android Studio `2025.1.4` user profile.
- [x] PICO Spatial Editor `6.0.0` is installed with the managed MCP launcher, backend, and runtime required by the Intelligent Plugins.
- [x] PICO Emulator `6.0.0` is installed.
- [x] The legacy Android AVD `Pico` exists, and a CLI-managed PICO AVD named `Pico_MVP` was created for reproducible project validation.
- [x] `Pico_MVP` booted successfully and was visible as `emulator-5554`; the unmodified app installed, launched, and remained running.
- [~] `adb`, `java`, and `javac` are not on the shell `PATH`; Android Studio and direct SDK/JBR paths remain usable.

### Agent tooling

- [x] Git `2.52.0` and GitHub CLI `2.97.0` are installed; `gh auth status` confirms authenticated GitHub access as `zill4`. The running Codex process predates the CLI install, so it currently invokes `C:\Program Files\GitHub CLI\gh.exe` directly.
- [x] Node.js `24.13.0` and npm `11.10.1` are installed; this exceeds the Intelligent Plugins prerequisite of Node.js 18+.
- [x] Public `@picoxr/pico-cli 0.4.2` is installed globally, is on `PATH`, and was confirmed current by `pico-cli update --check --format json`.
- [x] Global `@openai/codex` was updated from `0.104.0` to `0.147.0`; the older CLI did not provide the `codex plugin` command required by PICO setup.
- [x] Codex marketplace `pico-xr` is registered.
- [x] `pico-spatial-agentic-tools@pico-xr 0.4.1` is installed and enabled.
- [x] The plugin payload contains 16 PICO Spatial skills and MCP declarations for `pico-dev-knowledge` and `pico-spatial-editor`.
- [x] PICO plugin doctor, MCP doctor, and project-context doctor all pass.
- [x] Supporting tools are provisioned: agent-vault `6.0.4`, Graphify `0.5.5`, profiler utilities `0.1.0`, `uv 0.12.5`, and Primer CLI.
- [x] User-level `PICO_HOME` is configured as `C:\Users\justc\AppData\Local\PICO\sdk`.
- [x] Project context is recorded in `.pico-env.json` and `PICO-SPATIAL-AGENTIC-TOOLS.AGENTS.md`; `AGENTS.md` routes agents to that plugin guidance.
- [x] The current Codex session exposes the installed PICO Spatial skills, including environment, development-workflow, and emulator-operation guidance.
- [~] `pico-dev-knowledge` and `pico-spatial-editor` are configured and pass `pico-cli mcp doctor`, but their MCP calls were not exposed by the host in this session.
- [~] After the prescribed one-time Editor reinstall, `editor doctor` confirms the Editor is launchable and has its MCP launcher/backend/runtime with `usableForBootstrap: true`, while still emitting a contradictory `EDITOR_PARTIAL_INSTALLATIONS` metadata error. Do not repeat the uninstall/install loop without a new diagnosis.
- [~] The root doctor also warns that npm is unavailable even though `npm --version` returns `11.10.1` and npm successfully performed both global installs. Treat this as a Windows command-discovery diagnostic discrepancy unless an npm-backed PICO command actually fails.

### Repository state

- [x] This directory contains the Welcome Space reference application.
- [x] Root project name is `WelcomeSpace`.
- [x] Modules are `app` and `editor-asset`.
- [x] PICO Spatial SDK BOM is `6.0.0`.
- [x] Android Gradle Plugin is `8.8.0`; Gradle wrapper is `8.10.2`; Kotlin is `2.0.0`.
- [x] App compiles against and targets Android API 35; minimum API is 26.
- [x] App source compatibility and Kotlin JVM target are Java 11.
- [x] Main package/application ID is `com.pico.spatial.sample.welcomespace`.
- [x] The current architecture demonstrates a planar default `WindowContainer`, a volumetric model-inspection `WindowContainer`, and a Full Space `Stage`.
- [x] `editor-asset` contains the Spatial Editor project and USD/USDZ-style scene assets used to build `editor-asset.bundle`.
- [x] `start-pico-emulator.bat` starts the `Pico` AVD with the PICO emulator distribution rather than Android Studio's stock emulator.
- [x] With the documented process-local Spatial Editor `PATH` workaround, `spotlessCheck test :app:assembleDebug --no-daemon` passes and produces `app-debug.apk` plus `editor-asset.bundle`.
- [x] This folder is a Git repository on `main`, tracking `origin/main` at `https://github.com/zill4/pico-example-project.git`; the initial baseline was published in commit `f1ef28f`.

## PICO OS 6 context

PICO calls its PICO OS 6 model **General Spatial Computing**. The PICO Spatial Engine performs unified, system-level rendering so 2D and 3D content from multiple applications can coexist with consistent depth, occlusion, resource scheduling, and interaction rules.

The two spatial modes are:

- **Shared Space**: the default multitasking environment, where multiple applications can coexist. System-managed interaction favors privacy and predictable behavior.
- **Full Space**: a focused, immersive mode dominated by one application, with room for more flexible interaction where permissions allow it.

The two primary content hosts are:

- **WindowContainer**: a bounded, system-managed container available in planar or volumetric form. Planar containers suit familiar 2D information; volumetric containers suit bounded 3D content.
- **Stage**: an immersive 3D scene container. Opening one enters Full Space; closing/leaving Full Space ends the Stage. WindowContainers can also be attached within a Stage.

How this repository maps to those concepts:

- `Main.kt` registers every spatial container through `mainApp`.
- The default planar container hosts the Home, Furniture Library, and Decorate Space Compose UI.
- The volumetric container hosts bounded model inspection.
- The Stage hosts the immersive room and furniture-placement flow.
- `editor-asset` owns 3D scenes and assets; the app loads its generated bundle through `asset://editor-asset.bundle`.
- Custom ECS behavior currently includes model rotation.

## Setup checklist

The original estimate for a clean setup is roughly 30-60 minutes, heavily dependent on download speed. This machine already has most large downloads.

### 1. Accounts and tool manager

- [x] Install [PICO Developer Center](https://developer.picoxr.com/resources/).
- [ ] Create a PICO developer account if one does not already exist.
- [ ] Sign in to PICO Developer Center.
- [~] Let PICO Developer Center identify required downloads; confirm its UI shows no missing requirement.
- [x] PICO Developer Center `1.4.10` is present locally.

### 2. Supported Android Studio

- [x] Install a supported Android Studio `2025.1.x` release. The official PICO guide currently says not to use other version families for Spatial SDK setup.
- [x] This machine uses `2025.1.4`, one of the intended versions.
- [x] Install Android 15 / SDK Platform 35.
- [x] Install Sources for Android 35.
- Reference: [Android Studio download archive](https://developer.android.com/studio/archive).

### 3. PICO Spatial SDK tools

- [x] Install the PICO Spatial Plugin in Android Studio. Outside mainland China, select **PICO Spatial (Global)**; in mainland China, select **PICO Spatial (CN)**.
- [x] Restart Android Studio after plugin installation.
- [x] Open **Settings > Languages & Frameworks > PICO Spatial Tools**.
- [x] Enable **Show Package Details**, then install Spatial Editor and PICO Emulator.
- [x] Spatial Editor and Emulator `6.0.0` are present locally.
- Guides: [development environment](https://developer.picoxr.com/document/spatial-sdk/set-up-development-environment/), [Spatial Plugin](https://developer.picoxr.com/document/spatial-toolkit/install-spatial-plugin/), and [PICO Emulator](https://developer.picoxr.com/document/spatial-toolkit/install-pico-emulator/).

### 4. Baseline application

- [x] Obtain the [Welcome to PICO OS 6 sample](https://developer.picoxr.com/document/spatial-example/).
- [x] Open its root directory in Android Studio.
- [x] Confirm Gradle sync completes without errors.
- [x] Start the managed `Pico_MVP` AVD with `pico-cli`.
- [x] Confirm the emulator appears as `emulator-5554` in the PICO device list.
- [x] Install and launch the debug APK on the managed AVD.
- [x] Exercise Home, Furniture Library, volumetric inspection, Decorate Space, and the immersive room.
- [x] Record the screenshot, runtime diagnostics, build result, and emulator result in a dated work-session entry.

### 5. PICO Intelligent Plugins

Official repository: [PICO Intelligent Plugins](https://github.com/Pico-Developer/PICO-Intelligent-Plugins).

The current upstream instructions require Node.js 18+ and recommend the guided setup:

```powershell
npm install -g @picoxr/pico-cli
pico-cli setup
```

For non-interactive Codex-only setup, upstream documents:

```powershell
pico-cli setup --tool codex --plugin pico-spatial-agentic-tools --yes
```

Progress:

- [x] Node.js prerequisite satisfied.
- [x] Install public `@picoxr/pico-cli 0.4.2` globally.
- [x] Update global `@openai/codex` to a version with `codex plugin` support (`0.147.0` installed on 2026-08-18).
- [x] Run Codex-only setup from the project root.
- [x] Confirm `codex plugin list` reports `pico-spatial-agentic-tools@pico-xr 0.4.1` as `installed, enabled`.
- [x] Confirm PICO plugin, MCP, and project-context doctors pass.
- [ ] Close and reopen Codex; upstream notes that a new agent session is required to load plugin manifests, skills, and MCP servers.
- [ ] Ask the new agent to enumerate its available PICO tools.
- [ ] Run one harmless/read-only PICO tool as an end-to-end verification.
- [x] Record the installed plugin version, commands, dependencies, and current-session limitation here.

Installation record (2026-08-18):

```powershell
npm install -g @picoxr/pico-cli@0.4.2

# The first setup attempt exposed an outdated Codex CLI without plugin commands.
npm install -g @openai/codex@0.147.0

pico-cli setup --tool codex --plugin pico-spatial-agentic-tools --yes
pico-cli plugin doctor --tool codex --plugin pico-spatial-agentic-tools --format json
pico-cli mcp doctor --format json
pico-cli project context doctor --project "C:\Users\justc\code\welcomespace-0.13.3" --tool codex --plugin pico-spatial-agentic-tools --format json
```

The first `pico-cli setup` attempt installed supporting dependencies but could not register the plugin because global Codex CLI `0.104.0` rejected `codex plugin marketplace list`. After updating the official global CLI to `0.147.0`, rerunning the same setup command registered marketplace `pico-xr`, installed and enabled plugin `0.4.1`, copied the project context, and linked it from `AGENTS.md`.

PICO's doctor then found that the pre-existing Spatial Editor lacked the managed MCP launcher/backend/runtime. The prescribed one-time repair was:

```powershell
pico-cli editor uninstall -y
pico-cli editor install -y
```

The replacement Editor `6.0.0` passed checksum verification and now reports all managed capabilities present. MCP doctor reports both the Spatial knowledge graph and Editor bootstrap gateway ready. The remaining `EDITOR_PARTIAL_INSTALLATIONS` flag conflicts with those capability checks and persisted after the single supported repair, so it is documented rather than retried destructively.

## Curriculum

Progress through the curriculum in order. Each phase should leave behind a small, observable artifact or a dated note.

### Phase 0: Understand the platform

- [ ] Read the [PICO OS 6 overview](https://developer.picoxr.com/document/discover/pico-os-6-overview/).
- [ ] Explain Shared Space versus Full Space in project terms.
- [ ] Explain planar and volumetric WindowContainers versus a Stage.
- [ ] Read the [spatial design guidelines](https://developer.picoxr.com/document/spatial-design/) with attention to comfort, legibility, initial placement, immersion, and connection to the physical environment.
- Exit artifact: a short dated note describing which container each planned experience should use and why.

### Phase 1: Reproduce the sample

- [ ] Build and run the unmodified app in the PICO Emulator.
- [ ] Identify the launch Activity and `mainApp` entry point.
- [ ] Trace navigation from the planar UI into the volumetric inspection container and Full Space room.
- [ ] Open the Welcome Space editor project in Spatial Editor.
- Exit artifact: verified baseline run plus a five-sentence architecture summary.

### Phase 2: Learn the code and asset flow

- [ ] Read `Main.kt`, `MainNavHost.kt`, `ItemDisplayVolume.kt`, and `FullSpaceRoom.kt`.
- [ ] Trace dependency injection and one ViewModel-backed user flow.
- [ ] Trace a model from `ModelRepository` to a scene name, target node, preview, and placement.
- [ ] Understand when `editor-asset.bundle` is generated and how the app loads it.
- [ ] Inspect the existing ECS rotation system.
- Exit artifact: add a simple architecture map or notes to this document.

### Phase 3: Make a safe vertical change

- [ ] Change one label or visual style in the planar UI.
- [ ] Add or alter one catalog item using the existing data flow.
- [ ] Make one controlled Spatial Editor asset/scene change.
- [ ] Validate the complete flow in the emulator after each change.
- Exit artifact: one small end-to-end feature with validation notes.

### Phase 4: Interaction and spatial design

- [ ] Evaluate reach, target size, legibility, distance, and motion comfort.
- [ ] Test drag, pinch/scale, placement, and exit/back behavior.
- [ ] Confirm resource cleanup when containers close.
- [ ] Check shared-space behavior before adding immersive features.
- Exit artifact: a short interaction test matrix and any resulting decisions.

### Phase 5: Project 1 prototype

Four candidate directions are preserved. Candidate C is selected as the first slice, and Candidate D is the planned evolution toward the complete Chore Helper. The marker-by-marker execution plan is maintained in `PROJECT_01_ROOM_QUEST.md`.

#### Candidate A: Immersive sleep sounds / meditation

Build a small spatial-audio experience from a PICO immersive-audio sample or the Spatial SDK audio capabilities. A sensible first slice is a planar sound picker and timer that opens a calm Full Space Stage containing one or more positioned sound sources.

- [ ] Locate and run the official immersive/spatial audio sample.
- [ ] Decide whether the first slice requires Full Space or can start in Shared Space.
- [ ] Define an audio asset license and attribution policy before adding files.
- [ ] Implement play, pause, volume, timer, and exit behavior.
- [ ] Add spatial placement only after reliable lifecycle/audio focus behavior.
- [ ] Test looping, interruption, app backgrounding, and Stage closure.

#### Candidate B: Spatial recipe book

Adapt Welcome Space's catalog, planar navigation, volumetric inspection, and Stage patterns into recipes, ingredients, or cooking steps.

- [ ] Define the smallest recipe data model and one complete recipe.
- [ ] Map the catalog page to recipes or ingredients.
- [ ] Use a volumetric container only where 3D presentation materially helps.
- [ ] Decide whether a Full Space kitchen/stage is valuable for the first slice.
- [ ] Test legibility, input comfort, and progression through cooking steps.

#### Candidate C: Spatial Language Quest (selected first slice)

Turn the existing furniture room into a language-learning playground. The user points at or selects a known virtual object, sees a spatial label attached to it, hears the word, and then completes object-finding and placement prompts such as “find the lamp” or “put the cup on the shelf.” This preserves the simplicity of “Kore wa nan desuka?” while making position, direction, manipulation, and memory part of the learning loop.

- [ ] Attach language metadata to a small set of existing scene entities.
- [ ] Implement point/select, spatial label, pronunciation playback, and reveal/hide behavior.
- [ ] Add one retrieval challenge and one placement challenge with deterministic scoring.
- [ ] Provide text/button input as an emulator-safe fallback before live voice input.
- [ ] Test selection, labels, prompts, placement, scoring, reset, and Stage exit in the emulator.

#### Candidate D: Tidy Room Coach

Seed the virtual room with deliberately misplaced objects. A task planner orders cleanup steps, highlights the object to move in pink and its likely destination in green, validates placement, and awards progress. Known scene entities stand in for perception during the simulator proof of concept; real-room recognition remains a later device track.

- [ ] Define a small chore/task model and a deterministic “messy room” scenario.
- [ ] Reuse the existing Stage placement targets and Fresnel feedback for move/destination guidance.
- [ ] Implement logical task ordering, correct/incorrect placement feedback, scoring, and reset.
- [ ] Label the emulator input as simulated perception in demos and test evidence.
- [ ] Keep real camera inference, object tracking, and physical-room alignment out of MVP acceptance criteria.

The two leading directions can converge later into a “TidyTalk” mode: the user tidies the virtual room by following instructions in the language being learned. Do not combine them in the first vertical slice unless the individual selection, labeling, and placement loops are already reliable.

### Phase 6: Quality and release readiness

- [ ] Add unit tests for pure state/data logic and focused UI/instrumented tests where practical.
- [ ] Run formatting, tests, and a debug build.
- [ ] Review PICO's 3D rendering and performance guidance before optimizing scenes.
- [ ] Profile the representative experience on emulator and physical hardware when available.
- [ ] Replace sample identifiers, labels, icons, and content only after the prototype direction is chosen.
- [ ] Document permissions, privacy implications, licenses, and distribution requirements.

## Working commands

Android Studio supplies a compatible bundled JDK even though Java is not currently on the shell `PATH`. For PowerShell terminal builds on this machine:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
$env:PATH = "$env:LOCALAPPDATA\PICO\sdk\6.0\editor\SpatialEditor;$env:PATH"
.\gradlew.bat spotlessCheck test :app:assembleDebug --no-daemon
```

The additional `PATH` entry lets `spatialbundle.exe` resolve `nanobind.dll`, which PICO Editor `6.0.0` installed in the sibling `SpatialEditor` directory. Keep this workaround local to the process; do not add the workstation path to portable Gradle configuration.

Emulator and device checks:

```powershell
pico-cli emulator start --avd Pico_MVP --wait-timeout 180 -y
pico-cli device list --format json
```

The launcher intentionally points `ANDROID_SDK_ROOT` at PICO's emulator system images. Do not replace it with Android Studio's stock emulator command without revalidating the PICO AVD.

## Decision log

| ID | Date | Status | Decision | Reason / consequence |
| --- | --- | --- | --- | --- |
| D-001 | 2026-08-18 | Accepted | Use Welcome Space `0.13.3` as the starting codebase. | It already demonstrates Compose UI, planar and volumetric containers, a Full Space Stage, ECS behavior, 3D assets, and cross-container navigation. |
| D-002 | 2026-08-18 | Accepted | Keep project memory in this file and agent workflow rules in root `AGENTS.md`. | Future human and agent sessions need a shared record of verified progress and reasons behind changes. |
| D-003 | 2026-08-18 | Accepted | Hold the initial baseline at Android Studio `2025.1.4`, API 35, and Spatial SDK `6.0.0`. | These versions match the installed supported toolchain and current project. Upgrades should be explicit, separately tested decisions. |
| D-004 | 2026-08-18 | Superseded | Choose immersive sleep/meditation audio or spatial recipe book as Project 1. | New simulator-first XR concepts now better exercise the sample's object interaction and Stage placement patterns; the original ideas remain preserved as candidates. |
| D-005 | 2026-08-18 | Accepted | Upgrade the global Codex CLI from `0.104.0` to `0.147.0`. | PICO's supported installer requires `codex plugin` marketplace commands, which were absent from `0.104.0`. |
| D-006 | 2026-08-18 | Accepted | Make Project 1 simulator-first and represent perception with known virtual scene entities. | This can prove spatial selection, manipulation, guidance, state, and scoring without misrepresenting emulator output as real camera recognition or physical-room validation. |
| D-007 | 2026-08-18 | Accepted | Build Spatial Language Quest as the first vertical slice, then evolve it through Point-and-Place and Virtual Tidy Room Coach into Chore Helper. | This sequence proves the shared spatial mechanics in the emulator before adding AI planning and device-dependent perception. |
| D-008 | 2026-08-18 | Accepted | Track Project 01 in `PROJECT_01_ROOM_QUEST.md` using evidence-gated product markers `PM-0` through `PM-9`. | Each marker remains independently demoable and unchecked until its listed build, test, emulator, or device evidence exists. |
| D-009 | 2026-08-18 | Accepted | Implement the MVP conversation path in the order deterministic typed input, live typed AI, speech output, then measured push-to-talk input. | The simulator remains testable without microphone, network, or provider availability, while voice can be added without replacing product logic. |
| D-010 | 2026-08-18 | Accepted | Give the AI brain a bounded semantic scene snapshot and require locally validated, allowlisted product intents before changing Stage state. | This keeps high-frequency scene ownership in the app/ECS path, limits hallucination impact, and creates deterministic contract tests. |
| D-011 | 2026-08-18 | Accepted | Keep all Project 01 planning, requirements, experiments, decisions, and checkpoints in `PROJECT_01_ROOM_QUEST.md` under dated sections. | One navigable product history avoids Markdown-file bloat; future work should update this document instead of creating additional `PROJECT_01*.md` files. |
| D-012 | 2026-08-18 | Accepted | Add the installed `SpatialEditor` directory to the current build process `PATH` on this workstation instead of changing portable Gradle configuration. | This resolves PICO Editor `6.0.0`'s sibling `nanobind.dll` lookup failure while keeping machine-local SDK paths out of source control. |
| D-013 | 2026-08-18 | Accepted | Use `xr_headset`, `vase`, `headphones`, `art_print`, and `desk_lamp` as the first stable product IDs, mapped to the existing five catalog scenes and Full Space nodes. | Editor names such as `PicoEquipment` and `PicoEarphone` are implementation details and should not leak into AI prompts, tests, or persisted product state. |
| D-014 | 2026-08-18 | Accepted | Put virtual-scene discovery behind a minimal `PerceptionProvider` that emits semantic object IDs. | The emulator can supply deterministic known-entity snapshots now, while a later device provider can change sensing without changing product identity or claiming camera recognition. |
| D-015 | 2026-08-18 | Accepted | Keep deterministic room-session state in the domain/ViewModel path and use ECS components for runtime hit testing and visual feedback. | Product state remains unit-testable and does not depend on transient `Entity` wrapper instances; Stage transforms and high-frequency interaction stay in the scene path. |
| D-016 | 2026-08-18 | Accepted | Generate runtime box colliders from each model's actual visual bounds and add PICO `InteractableComponent` and `HoverEffectComponent`. | The existing editor assets remain untouched, all five current objects get consistent focus affordances, and the interaction seam can be removed during Stage cleanup. |
| D-017 | 2026-08-18 | Accepted | Resolve gesture targets with the SDK entity UUID and target the loaded room hierarchy with `TargetEntity.hit(roomRoot)`. | Callback wrappers should not become product identity, and hierarchy targeting covers interactable descendants of the single room entity added to `SpatialView`. |
| D-018 | 2026-08-18 | Accepted | Keep `PM-1` in progress until a direct 3D select/deselect callback is evidenced. | Automated emulator input produced visible hover, placement, reset, and exit evidence but no tap callback; build success and hover alone do not prove selection. |

## Open questions

- Which physical PICO device will eventually be used for real-room sensing and comfort validation?
- Should language learning and tidying remain separate modes or converge only after both core loops work independently?
- Which 5–8 existing room objects provide the best first Japanese learning set?
- Is PICO Developer Center account access already working?
- Should the sample package/application ID and product branding remain until after the first prototype?

## Work-session log

### 2026-08-18 — PM-1 placement evidence and overlapping-room diagnosis

Outcome:

- Recorded the user's manual result that add actions made the tested models appear and changed their cards to `In room`.
- Kept direct selection open because `In room` proves placement, while the required `Selected` label and `PM-1 tap [...]` callback/log evidence were not reported.
- Investigated the reported overlap between the app room and the emulator's starting room without changing app code, assets, or emulator state.

Validation:

- `pico-cli emulator status` reports the managed PICO Emulator `6.0.0` online as `emulator-5554`; app process `4227` is running.
- Runtime logs show one Stage named `room`, opened as `FULL` with immersion `100` and `useSystemEnvironment=false`. The Stage is focused, `PM-1 scene ready` reports five objects, and the crash buffer is empty.
- Repository inspection shows one `WelcomeSpace_VR` load, positioned at `(0.15, 0, -3.6)` with a `-30` degree yaw. No second application Stage or room-scene load was found.
- Queried the installed PICO OS 6 knowledge graph and SDK API reference. `StageStyle.Full` is documented to take over the display and disable Video See-through; therefore, an emulator base room visible while the Stage remains active is not accepted as intended Full Stage behavior.
- Captured `captures/pm1-double-room-report-2026-08-18.png` for comparison. The static capture confirms the current Stage/panel state but does not prove the reported disappearance transition.
- The environment preflight confirmed Node.js `24.13.0`, npm `11.10.1`, public `pico-cli 0.4.2`, healthy emulator and MCP checks, and the already documented contradictory Spatial Editor partial-installation flag. No repair was attempted.

Working diagnosis:

- The finite, interior-facing app room is probably being left behind or viewed from its culled side as the simulated HMD moves. If the emulator then reveals its base-room backdrop despite `useSystemEnvironment=false`, that is likely an emulator compositor/scene-boundary limitation. A transition recording is required before treating this as confirmed.

Next:

- Record the transition while watching whether the Decorate Space panel remains visible, then reset/recenter the emulator viewpoint. Use that result to separate finite-scene/background composition from an unexpected Stage close.
- Continue the direct selection acceptance pass separately; `PM-1` is not complete until three objects produce `Selected` and `DESELECTED` state/log evidence.

### 2026-08-18 — PM-1 manual emulator acceptance procedure

Outcome:

- Added a step-by-step `PM-1` PICO Emulator acceptance runbook to the existing `PROJECT_01_ROOM_QUEST.md`; no additional Project 01 Markdown file was created.
- Defined the exact build/deploy preflight, Eye Gesture input configuration, three-object select/deselect proof, reset/exit sequence, filtered `RoomScene` logs, recording requirement, and pass/fail classifications.
- Kept hover distinct from selection evidence and documented that 2D `adb shell input tap` is not a valid way to prove Stage interaction.
- Kept transient loading state distinct from a real unavailable-object result; runtime missing-node acceptance remains a future debug-fixture case rather than an editor-asset mutation.

Validation:

- Cross-checked the procedure against the installed PICO emulator workflow guidance, the current `Pico_MVP`/`emulator-5554` baseline, the implemented PM-1 UI states, and the existing `RoomScene` diagnostic messages.
- No Kotlin, Spatial Editor asset, build output, emulator state, or runtime behavior was changed or newly verified in this documentation step.

Next:

- Run the manual procedure, retain one continuous interaction recording plus filtered logs, and mark `PM-1` complete only if three stable object IDs pass select, deselect, reset, exit, and crash-free checks.

### 2026-08-18 — PM-1 spatial object foundation

Outcome:

- Added the five-object semantic catalog, strict IDs, centralized editor bindings, virtual `PerceptionProvider`, deterministic room session, and pure catalog/session tests.
- Reworked furniture/decorate UI boundaries to use stable product IDs and explicit `In room`, `Selected`, and `Unavailable` labels, with an explicit room reset action.
- Added bounds-derived collision, interactable and hover ECS components to the five runtime model entities, persistent Fresnel selection feedback, a room-hierarchy tap recognizer, and owner-based Stage/IBL cleanup.
- Used the PICO Spatial SDK skill guidance and local official SDK `6.0` documentation for collision/interactable requirements, `SpatialView` gesture wiring, hierarchy targeting, hover feedback, and lifecycle cleanup. No Spatial Editor content edit was required.
- The PICO development knowledge MCP was callable in this session and was used to cross-check the official tap and `SpatialView` patterns.

Validation:

- Focused `:app:testDebugUnitTest` passes for the new catalog/session cases.
- The PICO SpatialUI design-style verifier passes with zero errors and zero warnings.
- `spotlessApply spotlessCheck test :app:assembleDebug --no-daemon` passes on the final tree in 1 minute 17 seconds (109 tasks), including the new catalog/session tests; the resulting APK was installed in PICO Emulator `6.0.0`.
- Emulator logs confirmed five available room objects, stable-ID placement, and reset. The placed headset visibly received the PICO hover effect; reset hid it and restored the add action; Stage exit returned to Welcome Space; process `10452` remained alive and the crash buffer was empty.
- Direct 3D selection is not verified. Left-controller and Eye Gesture automation both focused the placed model but produced no `detectSpatialTapGesture` callback, so select/deselect and the three-object selection checkpoint remain open.

Next:

- Perform one manual emulator click (or physical-controller input) on the hovered headset and inspect `RoomScene` for `PM-1 tap [xr_headset]`; verify select and deselect for three objects, capture the `Selected` text state, then repeat reset and exit before completing `PM-1`.

### 2026-08-18 — PM-0 build and emulator validation

Outcome:

- Traced `spatialbundle.exe` exit `0xC0000135` through its transitive PE dependencies and identified missing-at-runtime `nanobind.dll`, imported by `spatial.foundation.dll`.
- Confirmed `nanobind.dll` is installed under `...\editor\SpatialEditor` while the packager runs from `...\editor\spatialbundle`.
- Used a process-local `PATH` addition to validate the packager without hard-coding the workstation SDK path into the repository.
- Created CLI-managed AVD `Pico_MVP`, installed the debug APK, launched the sample, and exercised the planar home, Furniture Library, PICO Art Print volumetric inspection, Full Space entry, and Full Space exit.
- Completed a fresh Android Studio Gradle sync without an unresolved error.
- Inventoried the five existing catalog/Stage objects and accepted stable IDs `xr_headset`, `vase`, `headphones`, `art_print`, and `desk_lamp` for the first slice.

Validation:

- `spatialbundle.exe --help` exits `0` when the installed `SpatialEditor` directory is on `PATH`.
- `:editor-asset:spatial_pack_task --no-daemon` passes and generates `editor-asset.bundle`.
- `spotlessCheck test :app:assembleDebug --no-daemon` passes in 1 minute 8 seconds; the current modules contain no unit-test sources, so their test tasks report `NO-SOURCE` rather than executing test cases.
- `pico-cli emulator doctor --format json` reports `SUCCESS`.
- `Pico_MVP` booted completely as `emulator-5554`; app `com.pico.spatial.sample.welcomespace` version `6.0.0` installed and reported `running: true` after launch.
- `captures/pm0-baseline-2026-08-18.png` exists locally and visibly contains the Welcome Space planar panel; the capture directory is ignored by Git.
- The app crash buffer returned no entries. Error-level emulator/Spatial runtime diagnostics were present but did not terminate the app.
- The five model cards map to standalone preview scenes and Full Space nodes under `Dynamic_Group`; Stage entry exercised the required lookup for every configured node without failure.

Next:

- Begin `PM-1` by adding the stable product object mapping and pure catalog/selection/snapshot tests before changing Stage UI.

### 2026-08-18 — Consolidated Project 01 requirements

Outcome:

- Consolidated the accepted `PM-0` through `PM-3` MVP requirements into a dated section of `PROJECT_01_ROOM_QUEST.md` and removed the standalone requirements file.
- Established `PROJECT_01_ROOM_QUEST.md` as the single Project 01 planning document; future requirements, experiments, decisions, and checkpoints will be added there under dated sections.
- Defined separate contracts for conversation input/output, fixture/live brain implementations, semantic scene context, reviewed language content, and validated spatial intents.
- Defined typed deterministic, live typed, speech-output, and microphone-spike test stages.
- Added the simulator runbook, brain contract cases, test layers, MVP boundaries, exit criteria, and open decisions.

Validation:

- Reviewed the requirements against current Welcome Space planar, Stage, ViewModel, ECS, object-catalog, placement, and lifecycle patterns.
- Reviewed official PICO guidance for emulator keyboard input, eye/pinch and controller interaction, recording, spatial/audio debugging, multimodal feedback, and comfort.
- PICO's public Emulator UI guidance reviewed in this session does not establish host microphone capture behavior; that capability remains an explicit measured spike.
- No Kotlin code, Gradle dependency, model provider, build, emulator, microphone, or live AI behavior was changed or tested in this documentation session.

Next:

- Begin `PM-1` with the stable product object mapping and pure catalog/selection/snapshot tests before adding product UI.

### 2026-08-18 — GitHub repository publication

Outcome:

- Confirmed Git `2.52.0` and GitHub CLI `2.97.0` are installed and authenticated as `zill4`.
- Confirmed `zill4/pico-example-project` is an empty public repository and the authenticated account has admin permission.
- Initialized this folder on `main` and connected `origin` to `https://github.com/zill4/pico-example-project.git`.
- Added `.tga` to the existing Git LFS policy before staging the full project.
- Staged 180 project files; 53 binary assets use LFS, including all 26 worktree files larger than 10 MB.
- Committed the baseline as `f1ef28f` (`Initialize PICO Room Quest project`) and pushed `main` without force to the empty target repository.
- Uploaded all 53 LFS objects, approximately 1.0 GB in total.

Validation:

- `git --version` returned `2.52.0.windows.1`.
- `gh auth status` succeeded, and repository inspection reported `isEmpty: true` with `ADMIN` permission.
- `local.properties`, IDE state, Gradle caches, and generated build outputs remain ignored.
- GitHub reports `main` as the default branch, and the local and remote commit IDs both resolve to `f1ef28f3936bd0ae84fec966f28f96c3228612a4`.
- `git lfs fsck` passes.
- `spotlessCheck` passes after applying the repository's native Windows line-ending format.
- The combined `test` and `:app:assembleDebug` run reached `:editor-asset:spatial_pack_task`, where PICO `spatialbundle.exe` exited with Windows code `-1073741515` (`0xC0000135`). Tests and debug assembly therefore remain incomplete pending runtime-dependency diagnosis.

Next:

- Diagnose the Spatial bundle tool dependency before marking `PM-0` complete.

### 2026-08-18 — Project 01 roadmap

Outcome:

- Accepted Spatial Language Quest as the first product slice and the complete Chore Helper as the end checkpoint.
- Added `PROJECT_01_ROOM_QUEST.md` with ten evidence-gated product markers spanning baseline, language learning, spatial placement, virtual tidying, AI planning, simulator POC, device perception, and final delivery.
- Set `PM-0 — Reproducible Baseline` as the active checkpoint.

Validation:

- Checked the roadmap against the current Welcome Space container, Stage, placement, highlight, data/ViewModel, ECS, and asset boundaries.
- Kept simulator and physical-device claims separated throughout the plan.
- Reviewed the created Markdown structure and cross-references; no Gradle build, emulator run, or device test was performed for this documentation-only change.

Next:

- Complete `PM-0`, capture the baseline emulator evidence, and inventory the objects and target nodes needed for `PM-1`.

### 2026-08-18 — Simulator-first XR concept exploration

Outcome:

- Compared a simple point-and-translate tool, a spatial language quest, and a room-tidying coach against spatial value, Welcome Space reuse, emulator testability, sensing risk, and engineering scope.
- Recommended Spatial Language Quest as the smallest vertical slice and Tidy Room Coach as the next mode; preserved a possible later “TidyTalk” combination.
- Established that emulator demos will use known virtual scene entities as simulated perception rather than claim real image recognition.

Validation:

- Reviewed the current Welcome Space architecture and its existing planar catalog, volumetric inspection, Full Space room, target-node placement, Fresnel feedback, and ECS patterns.
- Reviewed official PICO Emulator guidance confirming simulated VST backgrounds, spatial meshes, eye/pinch and controller interaction, navigation, recording, and spatial debug overlays.
- Reviewed official PICO Spatial SDK documentation for Sense Pack, spatial anchors, spatial mesh, tracking, and SpatialML/SecureMR direction.
- No source code, build, emulator runtime, physical sensing, comfort, occlusion, or camera inference was tested in this concept-only session.

Next:

- Confirm the first vertical slice, then write its one-page experience contract and emulator acceptance scenarios before changing application code.

### 2026-08-18 — PICO Intelligent Plugins installation

Outcome:

- Installed public `pico-cli 0.4.2` and confirmed it is current.
- Updated global Codex CLI to `0.147.0` to obtain the required plugin marketplace commands.
- Registered marketplace `pico-xr` and installed/enabled `pico-spatial-agentic-tools 0.4.1`.
- Provisioned the Spatial knowledge pack, Graphify, Primer, `uv`, and performance utilities.
- Added `.pico-env.json` and `PICO-SPATIAL-AGENTIC-TOOLS.AGENTS.md`; PICO setup appended the plugin-context route to root `AGENTS.md`.
- Reinstalled Spatial Editor `6.0.0` through `pico-cli` so its managed MCP launcher, backend, and runtime are present.

Validation:

- `codex plugin list` reports the PICO plugin as `installed, enabled`.
- `pico-cli plugin doctor` returns `SUCCESS` for Codex.
- `pico-cli mcp doctor` returns `SUCCESS`; the Spatial `6.0` graph exists, Graphify serve readiness passes, and Editor MCP readiness passes.
- `pico-cli project context doctor` returns `SUCCESS` and confirms the `AGENTS.md` route.
- Primer requirements are configured; templates are an optional skipped component.
- A new Codex session is still required before host-visible skills and MCP calls can be verified.
- The post-repair Editor has all required capabilities but retains a contradictory partial-installation metadata flag; the supported repair was not repeated.

Next:

- Open a new Codex session in this project, enumerate the PICO Spatial skills, and run a read-only `pico-dev-knowledge` query.

### 2026-08-18 — Documentation and environment baseline

Outcome:

- Added this running notes/curriculum document.
- Added root agent instructions in `AGENTS.md`.
- Reviewed the project structure, Gradle configuration, manifest, core source layout, editor assets, and emulator launcher.
- Reviewed current official PICO OS 6, environment, Spatial SDK structure, design, sample, and Intelligent Plugins sources.
- Inventoried the local workstation and distinguished verified setup from account/runtime steps that still require interactive confirmation.

Validation:

- Confirmed Android Studio `2025.1.4`, Android API/Sources 35, PICO Developer Center, Spatial Plugin, Spatial Editor, Emulator, and `Pico` AVD on disk.
- Confirmed the Android SDK and PICO SDK paths in local-only `local.properties`.
- Confirmed that no device was connected during the check.
- Confirmed Node/npm are available, `pico-cli` is not on `PATH`, and the current Codex session has no PICO-specific agent tools.
- Did not run a fresh Gradle build or launch the emulator in this documentation-only pass.

Next:

- Verify a clean Gradle sync and baseline emulator run, then add the result as the next dated entry.

## Primary references

- [PICO OS 6 landing page](https://developer.picoxr.com/pico-os-6/)
- [PICO OS 6 overview](https://developer.picoxr.com/document/discover/pico-os-6-overview/)
- [PICO developer resources and downloads](https://developer.picoxr.com/resources/)
- [PICO Spatial SDK development environment](https://developer.picoxr.com/document/spatial-sdk/set-up-development-environment/)
- [PICO spatial design guidelines](https://developer.picoxr.com/document/spatial-design/)
- [PICO Spatial Plugin installation](https://developer.picoxr.com/document/spatial-toolkit/install-spatial-plugin/)
- [PICO Emulator installation](https://developer.picoxr.com/document/spatial-toolkit/install-pico-emulator/)
- [PICO spatial sample / Welcome Space](https://developer.picoxr.com/document/spatial-example/)
- [PICO project structure and dependency configuration](https://developer.picoxr.com/document/spatial-sdk/project-structure-and-dependency-configuration/)
- [PICO Emulator UI and MR simulation](https://developer.picoxr.com/document/spatial-toolkit/pico-emulator-ui/)
- [SecureMR: privacy-first scene understanding](https://developer.picoxr.com/blog/securemr/)
- [PICO 3D rendering and performance analysis](https://developer.picoxr.com/document/spatial-sdk/3d-rendering-performance-analysis/)
- [PICO Intelligent Plugins on GitHub](https://github.com/Pico-Developer/PICO-Intelligent-Plugins)
- [OpenAI plugin installation and restart behavior](https://learn.chatgpt.com/docs/plugins)
- [Android Studio download archive](https://developer.android.com/studio/archive)
