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

1. Diagnose PICO `spatialbundle.exe` exit `0xC0000135`; formatting passes, but tests/debug assembly cannot complete until the missing Windows runtime dependency is resolved.
2. Open the project in Android Studio `2025.1.4` and confirm Gradle sync is clean.
3. Start the `Pico` AVD, deploy the unmodified app, and record the result.
4. Sign in to PICO Developer Center if needed and record only whether access works; never record credentials.
5. Start a new Codex session from this project and verify that the installed PICO skills plus `pico-dev-knowledge` and `pico-spatial-editor` MCP tools are exposed.
6. Complete `PM-0 — Reproducible Baseline` in `PROJECT_01_ROOM_QUEST.md` and save build/emulator evidence.
7. Inventory the room objects, scene names, target nodes, and placement/highlight code needed for `PM-1`.

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
- [x] Android Studio's bundled JBR is OpenJDK `21.0.8`.
- [x] Android SDK is configured in local-only `local.properties`.
- [x] Android SDK Platform 35 is installed.
- [x] Sources for Android 35 are installed.
- [x] PICO Developer Center `1.4.10` is installed.
- [~] PICO Developer Center account creation/sign-in cannot be verified from project files; confirm interactively.
- [x] PICO Spatial Plugin exists in the Android Studio `2025.1.4` user profile.
- [x] PICO Spatial Editor `6.0.0` is installed with the managed MCP launcher, backend, and runtime required by the Intelligent Plugins.
- [x] PICO Emulator `6.0.0` is installed.
- [x] A PICO AVD named `Pico` exists.
- [~] No emulator or physical device was connected when `adb devices -l` was checked.
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
- [~] The active Codex session predates installation and cannot hot-load the new skills/MCP tools. Open a new Codex session for host-visible verification.
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
- [~] Generated `app/build` and `editor-asset/build` output exists, but a fresh build was not run during this documentation pass.
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
- [ ] Confirm Gradle sync completes without errors.
- [ ] Start the `Pico` AVD from Android Studio or `start-pico-emulator.bat`.
- [ ] Confirm the emulator appears in `adb devices` and Android Studio's device selector.
- [ ] Run the `app` configuration.
- [ ] Exercise Home, Furniture Library, volumetric inspection, Decorate Space, and the immersive room.
- [ ] Record screenshots, failures, warnings, and baseline performance observations in a dated work-session entry.

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
.\gradlew.bat :app:assembleDebug
.\gradlew.bat test
.\gradlew.bat spotlessCheck
```

Emulator and device checks:

```powershell
.\start-pico-emulator.bat
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" devices -l
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

## Open questions

- Which physical PICO device will eventually be used for real-room sensing and comfort validation?
- Should language learning and tidying remain separate modes or converge only after both core loops work independently?
- Which 5–8 existing room objects provide the best first Japanese learning set?
- Is PICO Developer Center account access already working?
- Should the sample package/application ID and product branding remain until after the first prototype?
- For an audio app, where will licensed sounds come from and what attribution is required?
- Should this directory be initialized as a new Git repository or connected to an existing remote?

## Work-session log

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
