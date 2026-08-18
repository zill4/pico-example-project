/*
 * Copyright 2025 - 2026 PICO. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pico.spatial.sample.welcomespace

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pico.spatial.sample.welcomespace.data.assetBundle
import com.pico.spatial.sample.welcomespace.ui.display.ItemDisplayVolume
import com.pico.spatial.sample.welcomespace.ui.navigation.MainNavHost
import com.pico.spatial.sample.welcomespace.ui.room.FullSpaceRoom
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.foundation.dsl.DefaultWindowContainer
import com.pico.spatial.ui.foundation.dsl.Form
import com.pico.spatial.ui.foundation.dsl.Immersion
import com.pico.spatial.ui.foundation.dsl.SpatialAppScope
import com.pico.spatial.ui.foundation.dsl.Stage
import com.pico.spatial.ui.foundation.dsl.WindowContainer
import com.pico.spatial.ui.foundation.dsl.WindowContainerSize
import com.pico.spatial.ui.foundation.dsl.WorldScale
import com.pico.spatial.ui.platform.resize.ContainerResizeType
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun mainApp(scope: SpatialAppScope) =
    with(scope) {
        DefaultWindowContainer {
            PicoTheme { MainNavHost(Modifier.windowConstraints(width = 1280.dp, height = 720.dp)) }
        }

        WindowContainer(
            id = WINDOW_CONTAINER_DISPLAY_BOX_ID,
            resizeType = ContainerResizeType.ContentSize,
            defaultSize =
                WindowContainerSize(width = VOLUME_SIZE, height = VOLUME_SIZE, depth = VOLUME_SIZE),
            form = Form.Volumetric,
            worldScale = WorldScale.Fixed,
            enableMaterialBackground = false
        ) {
            PicoTheme {
                ItemDisplayVolume(
                    modifier =
                        Modifier.windowConstraints(
                            width = VOLUME_SIZE,
                            height = VOLUME_SIZE,
                            depth = VOLUME_SIZE
                        )
                )
            }
        }

        Stage(
            id = STAGE_ROOM_ID,
            immersion = Immersion(default = 100, min = 0, max = 100),
        ) {
            PicoTheme { FullSpaceRoom() }
        }

        MainScope().launch { assetBundle.await() }
    }

// Container ID
const val STAGE_ROOM_ID = "room"
const val WINDOW_CONTAINER_DISPLAY_BOX_ID = "display_box"

// Cross-container bundle parameter
const val CROSS_CONTAINER_BUNDLE_MODEL_NAME = "model_name"
const val CROSS_CONTAINER_BUNDLE_TITLE = "title"

// Design size
private val VOLUME_SIZE = 1200.dp
