# Project 01: Room Quest to Chore Helper

Status: Accepted direction; planning and baseline validation  
Started: 2026-08-18  
Current product marker: `PM-0 — Reproducible Baseline`  
Starting point: PICO Welcome Space `0.13.3`, Spatial SDK `6.0.0`

## Product goal

Build a simulator-first spatial learning experience that grows, one independently demoable product marker at a time, into an AI Chore Helper.

The first useful product is **Spatial Language Quest**: point at virtual household objects to learn their names, then find and place those objects by following instructions. Those same selection, highlighting, placement, planning, and scoring systems will become **Tidy Room Coach** and ultimately the full **Chore Helper** for a real room.

The final experience should let a user describe chores conversationally, receive a practical order based on time or effort, and get spatial guidance while completing them. Items needing attention are indicated with a pink warning treatment; suggested destinations use a green success treatment. Color must be reinforced by labels, shapes, or motion so it is never the only cue.

## Product promise

> Tell the assistant what you need to accomplish, then let your room become an interactive guide that helps you decide, act, and finish.

## Scope and evidence boundary

We do not currently have a physical PICO device. Development therefore has two explicit tracks:

- **Simulator track (`PM-0` through `PM-7`)**: use known virtual scene entities as simulated perception. This can validate product logic, spatial interaction, labels, highlighting, placement, planning, scoring, lifecycle, and the recorded proof-of-concept story.
- **Device track (`PM-8` and `PM-9`)**: investigate and validate real scene understanding, permissions, spatial alignment, tracking, occlusion, comfort, and performance on supported hardware.

Emulator evidence must never be described as proof of physical object recognition, camera inference, real-room alignment, or headset comfort.

## Marker rules

Status markers:

- `[x]` complete with recorded evidence
- `[~]` in progress or partially validated
- `[ ]` not started
- `[!]` blocked or awaiting a decision

Every product marker must:

1. Produce a user-visible, independently demoable outcome.
2. Preserve a deterministic path so a demonstration does not depend entirely on a live AI service.
3. Define normal, incorrect, empty, reset, and exit behavior where relevant.
4. Pass the smallest appropriate automated checks, a debug build, and the listed emulator or device scenario.
5. Record its evidence and decisions in this file and summarize the session in `PICO_DEVELOPMENT.md`.
6. Remain unchecked until the required evidence actually exists.

## Product marker map

| Marker | Product checkpoint | User-visible proof | Target environment | Status |
| --- | --- | --- | --- | --- |
| PM-0 | Reproducible Baseline | Unmodified Welcome Space launches and can be recorded | Emulator | [ ] |
| PM-1 | Spatial Object Foundation | User can select, identify, and reset known room objects | Emulator | [ ] |
| PM-2 | “Kore wa nan desuka?” Language Lens | Selected objects reveal an attached label and pronunciation | Emulator | [ ] |
| PM-3 | Spatial Language Quest | User finds prompted objects and earns progress | Emulator | [ ] |
| PM-4 | Point-and-Place Challenge | User follows a spatial instruction to move an object to its destination | Emulator | [ ] |
| PM-5 | Virtual Tidy Room Coach | User completes an ordered cleanup of a deliberately messy virtual room | Emulator | [ ] |
| PM-6 | Conversational Chore Planner | User describes chores and adjusts a structured plan by effort or time | Emulator | [ ] |
| PM-7 | Simulator Chore Helper POC | End-to-end recorded demo combines planning, spatial guidance, placement, and scoring | Emulator | [ ] |
| PM-8 | Real-Space Perception Gate | Supported device proves the minimum viable sensing and alignment approach | Physical device | [ ] |
| PM-9 | Complete Chore Helper | Real-room end-to-end experience meets the final product definition of done | Physical device | [ ] |

## PM-0 — Reproducible Baseline

Outcome: establish a trustworthy development and demonstration loop before changing product behavior.

Checkpoint:

- [ ] Android Studio opens the project and completes Gradle sync without an unresolved error.
- [ ] `spotlessCheck`, project tests, and `:app:assembleDebug` pass, or each concrete blocker is recorded.
- [ ] The `Pico` AVD starts and appears in `adb devices -l`.
- [ ] The unmodified app launches into its default planar experience.
- [ ] Furniture Library, volumetric inspection, and Full Space room entry/exit are exercised.
- [ ] At least one screenshot or short recording is saved as baseline evidence.
- [ ] Existing room objects, scene names, target nodes, and relevant interaction code are inventoried for `PM-1`.

Exit artifact: baseline build/test result, emulator capture, and a shortlist of 5–8 household objects for the first learning set.

## PM-1 — Spatial Object Foundation

Outcome: create the stable object model and interaction seam shared by language learning and tidying.

Planned scope:

- Define a product-level room-object record with a stable semantic ID, scene entity/node reference, display name, category, movable state, and optional destination reference.
- Represent emulator perception with known scene entities rather than camera claims.
- Add selection, deselection, focus feedback, reset, and unavailable-object behavior.
- Keep high-frequency 3D state and transforms in the existing ECS/scene path; keep session state in the surrounding ViewModel/domain patterns.
- Define the `PerceptionProvider` boundary only as far as this marker needs it. The first implementation reports virtual scene objects; a future implementation may report device perception results.

Checkpoint:

- [ ] At least three objects can be selected reliably in the Stage.
- [ ] Selected, unselected, and unavailable states are visibly distinct without depending on color alone.
- [ ] The app exposes stable object identity to product logic without leaking editor-only details throughout the UI.
- [ ] Reset restores the original object and selection state.
- [ ] Stage closure cleans up created entities, listeners, resources, and work owned by the feature.
- [ ] Focused automated tests cover pure object/session state.
- [ ] Emulator evidence shows select, deselect, reset, and exit.

Exit artifact: reusable spatial object contract and a recorded three-object interaction demo.

## PM-2 — “Kore wa nan desuka?” Language Lens

Outcome: make pointing at a household object produce a useful language-learning moment.

Working assumption: Japanese is the first content set, but language content should not be hard-wired into spatial interaction code.

Planned scope:

- Attach target-language text, reading/transliteration, native-language meaning, and pronunciation source to the selected object set.
- Show the label spatially associated with the selected object while keeping it readable and out of the manipulation path.
- Support an explicit “What is this?” action through an emulator-friendly control; live speech is not required yet.
- Provide replay, hide, missing-translation, missing-audio, and captions/text fallback behavior.
- Record content source, audio provenance, and usage rights before shipping imported assets.

Checkpoint:

- [ ] Five or more household objects have reviewed language metadata.
- [ ] Point/select reveals the correct attached label.
- [ ] Pronunciation can be played or a clearly labeled text fallback is presented.
- [ ] The label remains understandable from the expected emulator viewpoints.
- [ ] Missing content fails safely without losing navigation or selection.
- [ ] A short emulator recording demonstrates the complete question-and-answer loop.

Exit artifact: a demoable five-object spatial language lens.

## PM-3 — Spatial Language Quest

Outcome: turn object identification into an active spatial retrieval game.

Planned scope:

- Add “find the object” prompts using the `PM-2` content set.
- Add correct, incorrect, hint, skip, retry, completion, and reset states.
- Track session progress and a simple score or streak.
- Use direction and spatial location as part of the task; do not reduce the experience to a planar quiz.
- Provide readable prompts and a controller/mouse-compatible fallback for every essential action.

Checkpoint:

- [ ] A deterministic session presents at least five find-object prompts.
- [ ] Correct and incorrect selections produce clear, non-punitive feedback.
- [ ] Hint, skip, reset, and exit paths work.
- [ ] Scoring is deterministic and covered by focused unit tests.
- [ ] Stage close/reopen does not leave stale quest state unless resume is explicitly chosen.
- [ ] Emulator evidence shows one full quest from start to summary.

Exit artifact: the first complete Spatial Language Quest vertical slice.

## PM-4 — Point-and-Place Challenge

Outcome: establish the spatial manipulation loop that directly connects language learning to chore guidance.

Planned scope:

- Present an action instruction such as “Put the book on the shelf.”
- Indicate the source object with a pink attention treatment plus a non-color cue.
- Indicate the suggested destination with a green destination treatment plus a non-color cue.
- Reuse the sample's target-node placement and Fresnel-feedback patterns where they fit.
- Add drag/manipulate, valid destination, wrong destination, cancel, retry, and reset behavior.

Checkpoint:

- [ ] At least three object-to-destination tasks are supported.
- [ ] Source and destination remain distinguishable with color disabled or ignored.
- [ ] Correct placement is detected deterministically.
- [ ] Incorrect placement can be recovered without restarting the app.
- [ ] Original transforms are restored by reset and after the appropriate lifecycle transition.
- [ ] Emulator evidence shows one correct and one recovered incorrect placement.

Exit artifact: a reliable point-and-place lesson that supplies the core tidy interaction.

## PM-5 — Virtual Tidy Room Coach

Outcome: convert the placement mechanic into a useful, gamified virtual cleanup session.

Planned scope:

- Seed a deterministic messy-room scenario with several misplaced virtual objects.
- Create a structured chore model with priority, effort, estimated duration, dependencies, state, and destination.
- Offer simple session intents such as Quick Win, Balanced, and Deep Clean.
- Order the tasks using deterministic rules before introducing live AI planning.
- Guide one task at a time, allow reprioritization, and award transparent progress rather than arbitrary points.

Checkpoint:

- [ ] A four-or-more-task room can be generated and reset identically.
- [ ] Each effort mode produces an explainable task order.
- [ ] The user can complete, skip, defer, or reprioritize a task.
- [ ] Pink attention and green destination guidance use the `PM-4` interaction contract.
- [ ] Completion state and scoring survive ordinary UI recomposition and remain internally consistent.
- [ ] Emulator evidence shows the room changing from messy to complete.

Exit artifact: a fully playable Virtual Tidy Room Coach.

## PM-6 — Conversational Chore Planner

Outcome: let the user describe chores naturally and collaborate on a practical plan.

Planned scope:

- Accept typed/transcribed statements such as “I need to do laundry, dishes, declutter, and take out the trash.”
- Convert input into the structured chore model, surface assumptions, and ask only necessary follow-up questions.
- Let the user re-plan by available time, energy, urgency, room, or dependency.
- Explain the proposed order briefly and allow direct manual correction.
- Put live model access behind a planner interface with deterministic fixtures/fallbacks; never store credentials in the repository.
- Treat voice capture as a replaceable input adapter. Text input remains the reliable emulator path.

Checkpoint:

- [ ] At least five representative inputs produce a reviewable structured plan.
- [ ] Time/effort changes visibly reorder the plan for a defensible reason.
- [ ] Invalid, ambiguous, empty, offline, and model-error states are recoverable.
- [ ] The user can edit the result before beginning spatial guidance.
- [ ] Deterministic planner tests cover priority, dependency, and fallback behavior.
- [ ] Emulator evidence shows conversation/text input through acceptance of a plan.

Exit artifact: a reliable conversational planning flow connected to the virtual chore model.

## PM-7 — Simulator Chore Helper POC

Outcome: produce the shareable proof of concept that demonstrates the whole product story without pretending the emulator is a real home.

Demo story:

1. The user describes several chores.
2. The assistant proposes and explains an ordered plan based on the user's effort preference.
3. The user enters the messy virtual room.
4. Simulated perception reports the known room objects.
5. The current object and suggested destination receive spatial guidance.
6. The user completes tasks, earns progress, handles one mistake, and finishes the room.
7. The app presents a truthful session summary.

Checkpoint:

- [ ] `PM-1` through `PM-6` are integrated without bypassing their fallback or reset states.
- [ ] The demo can run deterministically without a network dependency.
- [ ] A visible note identifies scene input as simulated perception.
- [ ] Tests and a debug build pass.
- [ ] The full flow runs in the emulator without a blocking crash or lifecycle leak observed in the exercised path.
- [ ] A polished 60–120 second recording and a concise demo script are captured.
- [ ] Known limitations explicitly list all device-only claims still unverified.

Exit artifact: the simulator-complete Chore Helper proof of concept.

## PM-8 — Real-Space Perception Gate

Outcome: determine, with supported physical hardware, which real-room capabilities are sufficiently reliable for the final product.

This is a research and evidence gate, not an automatic implementation promise.

Planned investigations:

- Select and document the target PICO device and OS/SDK compatibility.
- Verify applicable permissions and privacy requirements.
- Evaluate available spatial mesh, plane/semantic data, anchors, tracking, and privacy-preserving perception options against the exact product need.
- Determine whether the app can identify relevant objects, understand destinations, maintain alignment, and observe meaningful completion states.
- Measure lighting, clutter, occlusion, distance, tracking loss, recovery, latency, comfort, and performance.
- Keep raw/sensitive environmental data on the narrowest supported path and disclose what is processed.

Checkpoint:

- [ ] Target hardware and supported SDK path are documented.
- [ ] A minimum three-object real-space experiment is run on-device.
- [ ] Spatial guidance remains acceptably aligned during the defined test movement.
- [ ] Tracking loss and permission denial recover safely.
- [ ] Privacy behavior and data flow are documented accurately.
- [ ] Evidence distinguishes confirmed capability, limitation, workaround, and rejected approach.
- [ ] The final `PerceptionProvider` scope is accepted or the product promise is narrowed.

Exit artifact: an evidence-backed go, narrow, or stop decision for complete Chore Helper perception.

## PM-9 — Complete Chore Helper

Outcome: deliver the end checkpoint—a real-room Chore Helper validated on supported PICO hardware.

Final user journey:

1. The user speaks or types the chores they want to complete.
2. The assistant creates an editable plan based on urgency, dependencies, time, and desired effort.
3. The user enters spatial guidance and grants any required permissions with clear context.
4. The app recognizes or confirms relevant room objects and destinations within the accepted `PM-8` capability boundary.
5. Pink attention guidance and green destination guidance help the user complete one task at a time.
6. The user can correct recognition, reprioritize, pause, resume, or finish manually.
7. Progress and points reflect confirmed actions, and the session ends with a useful summary.

Final definition of done:

- [ ] Planning, guidance, correction, completion, pause/resume, reset, and exit form one coherent journey.
- [ ] Real-world perception meets the reliability boundary accepted at `PM-8`.
- [ ] Essential information is conveyed through more than color and audio alone.
- [ ] Permissions, environmental data, model input/output, retention, and deletion behavior are understandable and documented.
- [ ] Offline/model/sensing failures preserve user control and never fabricate task completion.
- [ ] Automated checks, debug/release builds, and the full on-device acceptance matrix pass.
- [ ] Comfort, performance, tracking, occlusion, lighting, and representative-room results are recorded.
- [ ] Assets and language/audio content have documented provenance and acceptable usage rights.
- [ ] A final on-device demo recording and release-readiness summary exist.

Exit artifact: the whole Chore Helper, with honest device evidence and a documented supported scope.

## Architecture guardrails

- Preserve the sample's separation between planar SpatialUI, volumetric inspection, Full Space Stage, ViewModel/domain state, ECS behavior, and editor-owned assets.
- Use the planar WindowContainer for session setup, plan review, preferences, and summaries.
- Use the Stage only for tasks whose value depends on spatial direction, object location, manipulation, or room-scale simulation.
- Keep the Volumetric WindowContainer optional; use it only if close object inspection materially helps learning or correction.
- Use SpatialUI wrapped in `PicoTheme` for 2D UI. Do not introduce Material or Material3.
- Keep per-frame, sensed, and transform-heavy 3D behavior out of Compose recomposition; prefer ECS and explicit scene updates.
- Pair every opened container, listener, coroutine, loaded resource, entity, and tracking session with lifecycle cleanup.
- Keep live AI, speech, pronunciation, and perception behind narrow adapters with deterministic test doubles.
- Never invent PICO APIs or treat a planned platform capability as verified implementation evidence.

## Product data seams

These are conceptual boundaries, not final API names:

- `RoomObject`: stable product identity and spatial behavior for a household object.
- `LanguageEntry`: target text, reading, meaning, audio/caption source, and provenance.
- `PlacementTarget`: destination identity, acceptance rule, and guidance presentation.
- `ChoreTask`: priority, effort, duration, dependency, location, state, and completion evidence.
- `QuestSession`: prompt order, attempts, hints, score, and completion state.
- `PerceptionProvider`: available objects and their reported spatial state.
- `TaskPlanner`: natural-language or deterministic input to an editable chore plan.
- `SpeechInput` and `PronunciationOutput`: replaceable voice-related adapters with text fallbacks.

Names may change during implementation. New abstractions should be introduced only when the active marker requires them.

## Cross-marker acceptance matrix

| Area | Emulator acceptance | Physical-device acceptance |
| --- | --- | --- |
| Selection | Known objects can be targeted and reset | Physical targeting remains stable under representative movement |
| Labels | Correct content is attached and readable in simulated views | Readability and occlusion are comfortable in the headset |
| Manipulation | Drag/place and recovery are deterministic | Hand/controller interaction is comfortable and robust |
| Perception | Known scene entities produce declared simulated results | Accepted sensing path produces measured real-room results |
| Planning | Fixtures and optional live AI yield editable structured tasks | Voice/network behavior works within documented constraints |
| Guidance | Pink/green plus non-color cues direct the virtual task | Cues remain aligned, visible, and safe in a physical room |
| Completion | Placement/state rules drive transparent progress | Confirmed evidence never overstates real-world completion |
| Lifecycle | Stage/container reset and cleanup are exercised | Pause, resume, tracking loss, and permission changes recover |
| Performance | Build and emulator path have no blocking defect | Measured device performance meets the accepted target |

## Immediate working backlog

1. Complete `PM-0` and save baseline evidence.
2. Inventory the existing Welcome Space room objects, scene names, target nodes, and placement/highlight code.
3. Select the initial 5–8 household objects based on asset availability and spatial variety.
4. Draft and review the Japanese metadata for that object set, including content/audio provenance.
5. Write the `PM-1` object-state tests before implementing product UI changes.
6. Implement and verify one marker at a time; do not begin `PM-2` until `PM-1` evidence is recorded.

## Checkpoint log

Add one entry when a marker starts, changes materially, or completes.

### 2026-08-18 — Roadmap accepted

- Status: Project direction accepted; `PM-0` is next.
- Decision: Start with Spatial Language Quest and grow it through Point-and-Place and Virtual Tidy Room Coach into the complete Chore Helper.
- Evidence: Product plan reviewed against the current Welcome Space capabilities and simulator-only constraint. No build or runtime validation was performed for this documentation checkpoint.
- Next: Run the unchanged sample in PICO Emulator and capture `PM-0` evidence.

