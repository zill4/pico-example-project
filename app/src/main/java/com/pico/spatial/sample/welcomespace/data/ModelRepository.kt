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
package com.pico.spatial.sample.welcomespace.data

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.pico.spatial.sample.welcomespace.R

// Name of USDA scenes to be loaded and displayed in Furniture Library and Display Volume
private const val SCENE_EARPHONE = "PicoEarphone"
private const val SCENE_EQUIPMENT = "PicoEquipment"
private const val SCENE_LAMP = "PicoDeskLamp"
private const val SCENE_PAINTING = "PicoPainting"
private const val SCENE_VASE = "PicoVase"
const val SCENE_ROOM = "WelcomeSpace_VR"

// Name of items to be added into the full-space Room
private const val NODE_EARPHONE = "SM_Picoearphone_001"
private const val NODE_EQUIPMENT = "SM_Picoequipment_001"
private const val NODE_LAMP = "SM_Picodesklamp_001"
private const val NODE_PAINTING = "SM_PicoPainting_001"
private const val NODE_VASE = "SM_Picovase_001"

data class ModelCard(
    val modelBoundingBoxSize: DpSize,
    val targetItemSceneName: String,
    val titleResourceId: Int,
    val descriptionResourceId: Int,
    val targetItemNodeName: String,
)

val modelCards =
    listOf(
        ModelCard(
            DpSize(440.dp, 440.dp),
            SCENE_EQUIPMENT,
            R.string.item_name_headset,
            R.string.item_description_headset,
            NODE_EQUIPMENT,
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            SCENE_VASE,
            R.string.item_name_vase,
            R.string.item_description_vase,
            NODE_VASE,
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            SCENE_EARPHONE,
            R.string.item_name_earphones,
            R.string.item_description_earphones,
            NODE_EARPHONE,
        ),
        ModelCard(
            DpSize(210.dp, 210.dp),
            SCENE_PAINTING,
            R.string.item_name_painting,
            R.string.item_description_painting,
            NODE_PAINTING,
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            SCENE_LAMP,
            R.string.item_name_lamp,
            R.string.item_description_lamp,
            NODE_LAMP,
        ),
    )
