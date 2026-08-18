# Welcome Space

[English](README.md) | [简体中文](README_zh.md)

Welcome Space 是一款基于 PICO OS 6 的官方示例应用，集中展示了使用 PICO Spatial SDK 进行空间应用开发的最佳实践，涵盖 WindowContainer 与 Stage 管理、3D 模型交互以及实体组件系统（ECS）架构等核心能力。

## 概览

本应用将用户带入一个交互式 3D 空间，支持自由浏览、检视与布置家具。对于开发者而言，它同时也是一份实践参考，演示了如何将标准 UI 与沉浸式 3D 内容相结合、管理空间交互，以及借助 Spatial SDK 实现房间的个性化定制。
![Screenshot](.github/screenshot.png)

## 功能特性

Welcome Space 围绕开发者常用的流程与系统进行组织，展示了 PICO Spatial SDK 的实用开发模式：

- **应用流程**
  - **主页**：应用入口，可跳转至家具浏览页，或进入房间并开启装饰模式
  - **家具模型**：通过交互式 3D 模型，浏览家具物品
  - **模型查看**：在 Volumetric WindowContainer 中近距离查看模型，底部工具栏提供多种查看选项
  - **装饰空间**：进入沉浸式 Stage，选择并将物品摆放至房间场景

- **交互与控制**
  - **页面预览**：支持单手拖拽，旋转家具模型页与装饰空间页中的模型
  - **模型查看**：支持单手拖拽旋转和双手捏合缩放
  - **查看工具栏**：提供自转开关、光照开关、名称标签显隐和重置变换功能
  - **放置反馈**：在 Stage 中基于目标节点完成物品放置，并以菲涅尔（Fresnel）高亮效果给予视觉反馈

- **Spatial SDK 核心概念**
  - **WindowContainers**
    - **Planar WindowContainer**：呈现主页、家具模型页面、装饰空间页面的内容
    - **Volumetric WindowContainer**：用于模型查看功能，展示边界受限的 3D 内容
  - **Stage**：用于房间场景，呈现具有沉浸感的 Full Space 环境
  - **跨容器通信**：通过 WindowContainer 的 `bundle` 参数传递模型标识和标题信息
  - **生命周期管理**：将 Stage 和 WindowContainer 的开启/关闭与 UI 的导航和销毁深度绑定

- **3D 内容管线**
  - **编辑器场景打包**：使用 `editor-asset` 模块构建 `editor-asset.bundle`
  - **资源包加载**：对 `asset://editor-asset.bundle` 进行延迟加载
  - **模型目录**：集中管理物品定义，涵盖场景名称、目标节点名称及预览尺寸
  - **房间场景与锚点**：加载 `WelcomeSpace_VR` 并为每个物品解析预定义的目标节点

- **渲染与画质**
  - **基于图像的光照（IBL）**：从 assets 目录加载 `ibl.ktx`，提供环境光照效果
  - **基于 ECS 的行为系统**：通过自定义的 ECS 旋转系统实现模型的自转逻辑
  - **高性能加载机制**：基于协程的异步加载与资源按需清理

## 环境要求

构建和运行本项目前，请参照 PICO Spatial SDK 官方文档完成开发环境的配置：

- [PICO Spatial SDK 环境配置指南（中国大陆）](https://developer-cn.picoxr.com/document/spatial-sdk/set-up-development-environment/)
- [PICO Spatial SDK 环境配置指南（非中国大陆）](https://developer.picoxr.com/document/spatial-sdk/set-up-development-environment/)

## 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd WelcomeSpace
```

### 2. 在 Android Studio 中打开项目

启动 Android Studio，选择 **Open an Existing Project**，然后选择 `WelcomeSpace` 目录。

### 3. 运行项目

#### 在模拟器上运行

1. 打开 **Device Manager**（通过工具栏或 Tools > Device Manager 进入）
2. 点击 **Add a new device** > **Create a PICO Emulator**
3. 在 **Device Manager** 中启动模拟器
4. 点击 **Run 'app'** 启动应用

#### 在物理设备上运行

1. 将 PICO 设备连接至开发机
2. 在设备上启用 **USB 调试（USB debugging）**
3. 点击 **Run 'app'** 将应用直接部署到设备

## 项目结构

Welcome Space 采用模块化架构，将应用逻辑与 3D 资源分离：

```txt
WelcomeSpace/
├── app/                                  # 主应用模块
│   └── src/main/
│       ├── java/com/pico/spatial/sample/welcomespace/ # Kotlin 源代码文件
│       │   ├── Main.kt                     # 应用入口，定义了 WindowContainers 和 Stage
│       │   ├── data/                       # 数据层和 Repository
│       │   │   └── ModelRepository.kt      # 家具数据管理
│       │   ├── di/                         # 依赖注入（Koin）
│       │   ├── ecs/                        # 实体组件系统（ECS）逻辑
│       │   │   └── Rotation.kt             # 用于实体旋转的自定义 ECS 系统
│       │   ├── platform/                   # 平台相关实现
│       │   │   └── SpatialApplication.kt   # 应用初始化和 Koin 配置
│       │   ├── ui/                         # UI 组件和 ViewModels
│       │   │   ├── common/                 # 共享 UI 组件
│       │   │   ├── decorate/               # 装饰流程 UI
│       │   │   ├── display/                # 模型展示 UI
│       │   │   │   └── ItemDisplayVolume.kt # 用于模型检查的容器
│       │   │   ├── furniture/              # 家具库 UI
│       │   │   ├── home/                   # 主页 UI
│       │   │   ├── navigation/             # 导航图
│       │   │   │   └── MainNavHost.kt      # 应用导航逻辑
│       │   │   └── room/                   # 全空间房间 UI
│       │   │       └── FullSpaceRoom.kt    # 沉浸式房间实现
│       ├── assets/                         # 应用资源（IBL 等）
│       ├── res/                            # Android 资源
│       └── AndroidManifest.xml             # 包含 Spatial 组件声明的应用清单
├── editor-asset/                         # 3D 资源和场景定义
│   └── src/main/res3d/WelcomeSpace/      # Spatial Editor 项目内容
├── gradle/                               # Gradle 依赖和脚本
├── build.gradle.kts                      # 根构建配置
└── settings.gradle.kts                   # 项目设置和 SDK 配置
```

## 编辑 3D 资源

如需修改或扩展 Welcome Space 中的 3D 资源，请按以下步骤操作：

1. 在 Android Studio 中，展开 **editor-asset** 模块
2. 导航至 **res3d/WelcomeSpace/ModelView** 目录
3. 点击右上角的 **Open in Editor** 启动 PICO Spatial Editor
4. 在编辑器中调整场景布局、添加新模型或修改现有资产

![Spatial Editor](.github/editor.png)

## 学习资源

深入了解空间应用开发，欢迎访问官方开发者网站获取完整资源：

- [PICO 开发者网站（中国大陆）](https://developer-cn.picoxr.com/)
- [PICO 开发者网站（非中国大陆）](https://developer.picoxr.com/)

## 许可证

© 2026 PICO。本项目的源代码依据 [Apache 2.0 License](LICENSE.txt) 协议开源。本项目的美术资产（包括但不限于纹理、3D 模型和音频）基于 [Creative Commons BY 4.0](https://creativecommons.org/licenses/by/4.0/) 授权。

## 支持

如有任何问题、疑惑或功能建议，欢迎通过 [PICO 开发者支持门户](https://picodevsupport.freshdesk.com/support/home) 提交工单与我们联系。

---

为空间应用开发者提供的官方参考示例
