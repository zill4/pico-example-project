# Welcome Space

[English](README.md) | [简体中文](README_zh.md)

Welcome Space is a reference application for PICO OS 6 demonstrating best practices with the PICO Spatial SDK. It showcases core capabilities including WindowContainer and Stage management, 3D model interaction, and Entity Component System (ECS) architecture.

## Overview

This application invites users into an interactive 3D space to browse, inspect, and arrange furniture. It serves as a practical guide for developers, illustrating how to combine standard UI with immersive 3D content, manage spatial interactions, and implement room customization using the Spatial SDK.

![Screenshot](.github/screenshot.png)

## Features

Welcome Space demonstrates practical PICO Spatial SDK patterns, organized around the flows and systems developers typically care about:

- **User Flows**
  - **Home**: entry point to browse the furniture catalog or enter the room and start decorating
  - **Furniture Library**: browse furniture items with interactive 3D previews
  - **Model Inspection**: open a Volumetric WindowContainer to examine a model up close, with multiple viewing options in the bottom toolbar
  - **Decorate Space**: open an immersive Stage and select items to place in the room scene

- **Interaction & Controls**
  - **Page preview**: single-hand drag to rotate models on the Furniture Library and Decoration Space pages
  - **Model inspection**: drag to rotate and pinch to scale
  - **Inspection toolbar**: auto-rotate toggle, lighting toggle, name tag toggle, and reset transform
  - **Placement feedback**: node-targeted placement in Stage with Fresnel highlight visual feedback

- **Spatial SDK Concepts**
  - **WindowContainers**
    - **Planar WindowContainer**: renders Home, Furniture Library, and Decorate Space pages
    - **Volumetric WindowContainer**: powers the model inspection feature for bounded 3D content display
  - **Stage**: presents a room scene as an immersive Full Space environment
  - **Cross-container communication**: pass model identity and title via WindowContainer `bundle`
  - **Lifecycle management**: open/close of Stage and WindowContainers tied to UI navigation and disposal

- **3D Content Pipeline**
  - **Editor scene bundle**: `editor-asset` module builds `editor-asset.bundle`
  - **Bundle loading**: lazy load `asset://editor-asset.bundle`
  - **Model catalog**: centralized item definitions including scene name, target node name, and preview sizing
  - **Room scene & anchors**: load `WelcomeSpace_VR` and resolve predefined target nodes for each item

- **Rendering & Quality**
  - **Image-Based Lighting (IBL)**: load `ibl.ktx` from assets for environment lighting
  - **ECS-driven behaviors**: custom ECS rotation system for model auto-rotate logic
  - **Performance-minded loading**: coroutine-based async loading and on-demand resource cleanup

## Requirements

To build and run this project, configure your development environment by following the official PICO Spatial SDK setup guide:

- [PICO Spatial SDK Setup Guide (Outside Chinese Mainland)](https://developer.picoxr.com/document/spatial-sdk/set-up-development-environment/)
- [PICO Spatial SDK Setup Guide (Chinese Mainland)](https://developer-cn.picoxr.com/document/spatial-sdk/set-up-development-environment/)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd WelcomeSpace
```

### 2. Open in Android Studio

Launch Android Studio, select **Open an Existing Project**, and choose the `WelcomeSpace` directory.

### 3. Run the App

#### On Emulator

1. Open **Device Manager** (toolbar or **Tools > Device Manager**)
2. Click **Add a new device** > **Create a PICO Emulator**
3. Start the emulator from **Device Manager**
4. Click **Run 'app'** to launch the app

#### On Physical Device

1. Connect your PICO device to your development machine
2. Enable **USB Debugging** on the device
3. Click **Run 'app'** to deploy directly to your device

## Project Structure

WelcomeSpace follows a modular architecture to separate application logic from 3D resources:

```txt
WelcomeSpace/
├── app/                                  # Main application module
│   └── src/main/
│       ├── java/com/pico/spatial/sample/welcomespace/ # Kotlin source files
│       │   ├── Main.kt                     # App entry point defining WindowContainers and Stage
│       │   ├── data/                       # Data layer and repositories
│       │   │   └── ModelRepository.kt      # Manages furniture data
│       │   ├── di/                         # Dependency injection (Koin)
│       │   ├── ecs/                        # Entity Component System logic
│       │   │   └── Rotation.kt             # Custom ECS rotation system
│       │   ├── platform/                   # Platform-specific implementations
│       │   │   └── SpatialApplication.kt   # App initialization and Koin setup
│       │   ├── ui/                         # UI components and ViewModels
│       │   │   ├── common/                 # Shared UI components
│       │   │   ├── decorate/               # Decoration flow UI
│       │   │   ├── display/                # Model display UI
│       │   │   │   └── ItemDisplayVolume.kt # Volumetric view for model inspection
│       │   │   ├── furniture/              # Furniture library UI
│       │   │   ├── home/                   # Home screen UI
│       │   │   ├── navigation/             # Navigation graph
│       │   │   │   └── MainNavHost.kt      # App navigation logic
│       │   │   └── room/                   # Full space room UI
│       │   │       └── FullSpaceRoom.kt    # Immersive room implementation
│       ├── assets/                         # App assets (IBL, etc.)
│       ├── res/                            # Android resources
│       └── AndroidManifest.xml             # App manifest with Spatial component declarations
├── editor-asset/                         # 3D resources and scene definitions
│   └── src/main/res3d/WelcomeSpace/      # Spatial Editor project content
├── gradle/                               # Gradle dependencies and scripts
├── build.gradle.kts                      # Root build configuration
└── settings.gradle.kts                   # Project settings and SDK configuration
```

## Editing 3D Assets

To modify or extend the 3D assets in Welcome Space:

1. In Android Studio, expand the **editor-asset** module
2. Navigate to **res3d/WelcomeSpace/ModelView**
3. Click **Open in Editor** in the top-right corner to launch the PICO Spatial Editor
4. Use the editor to adjust the scene layout, add new models, or modify existing assets

![Spatial Editor](.github/editor.png)

## Learning Resources

To deepen your understanding of spatial app development, explore the resources on our official developer websites:

- [PICO Developer Website (Outside Chinese Mainland)](https://developer.picoxr.com/)
- [PICO Developer Website (Chinese Mainland)](https://developer-cn.picoxr.com/)

## License

© 2026 PICO. The project's source code is licensed under the [Apache 2.0 License](LICENSE.txt). All assets, including textures, 3D models, audio, and others, are licensed via [Creative Commons BY 4.0](https://creativecommons.org/licenses/by/4.0/).

## Support

For questions, issues, or feature requests, please contact us through the [PICO Developer Support Portal](https://picodevsupport.freshdesk.com/support/home) and submit a ticket.

---

Developed as a reference for spatial application developers
