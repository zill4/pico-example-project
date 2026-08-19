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

const val SCENE_ROOM = "WelcomeSpace_VR"

data class ModelCard(
    val modelBoundingBoxSize: DpSize,
    val roomObject: RoomObject,
) {
    val objectId: RoomObjectId
        get() = roomObject.id

    val targetItemSceneName: String
        get() = roomObject.sceneReference.sceneEntityName

    val titleResourceId: Int
        get() = roomObject.displayNameResourceId

    val descriptionResourceId: Int
        get() = roomObject.descriptionResourceId

    val targetItemNodeName: String
        get() = roomObject.sceneReference.sceneNodeName
}

val modelCards =
    listOf(
        ModelCard(
            DpSize(440.dp, 440.dp),
            RoomObjectCatalog.require(RoomObjectIds.XR_HEADSET),
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            RoomObjectCatalog.require(RoomObjectIds.VASE),
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            RoomObjectCatalog.require(RoomObjectIds.HEADPHONES),
        ),
        ModelCard(
            DpSize(210.dp, 210.dp),
            RoomObjectCatalog.require(RoomObjectIds.ART_PRINT),
        ),
        ModelCard(
            DpSize(180.dp, 180.dp),
            RoomObjectCatalog.require(RoomObjectIds.DESK_LAMP),
        ),
    )
