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

import com.pico.spatial.sample.welcomespace.R

@JvmInline
value class RoomObjectId(val value: String) {
    init {
        require(value.matches(SEMANTIC_ID_PATTERN)) {
            "Room object IDs must use lower_snake_case: $value"
        }
    }

    override fun toString(): String = value

    private companion object {
        val SEMANTIC_ID_PATTERN = Regex("[a-z][a-z0-9]*(?:_[a-z0-9]+)*")
    }
}

object RoomObjectIds {
    val XR_HEADSET = RoomObjectId("xr_headset")
    val VASE = RoomObjectId("vase")
    val HEADPHONES = RoomObjectId("headphones")
    val ART_PRINT = RoomObjectId("art_print")
    val DESK_LAMP = RoomObjectId("desk_lamp")
}

enum class RoomObjectCategory {
    DECOR,
    ELECTRONICS,
    LIGHTING,
}

data class RoomObjectSceneReference(val sceneEntityName: String, val sceneNodeName: String)

data class RoomObject(
    val id: RoomObjectId,
    val sceneReference: RoomObjectSceneReference,
    val displayNameResourceId: Int,
    val descriptionResourceId: Int,
    val category: RoomObjectCategory,
    val movable: Boolean,
    val destinationId: RoomObjectId? = null,
)

object RoomObjectCatalog {
    val objects =
        listOf(
            RoomObject(
                id = RoomObjectIds.XR_HEADSET,
                sceneReference =
                    RoomObjectSceneReference(
                        sceneEntityName = "PicoEquipment",
                        sceneNodeName = "SM_Picoequipment_001",
                    ),
                displayNameResourceId = R.string.item_name_headset,
                descriptionResourceId = R.string.item_description_headset,
                category = RoomObjectCategory.ELECTRONICS,
                movable = true,
            ),
            RoomObject(
                id = RoomObjectIds.VASE,
                sceneReference =
                    RoomObjectSceneReference(
                        sceneEntityName = "PicoVase",
                        sceneNodeName = "SM_Picovase_001",
                    ),
                displayNameResourceId = R.string.item_name_vase,
                descriptionResourceId = R.string.item_description_vase,
                category = RoomObjectCategory.DECOR,
                movable = true,
            ),
            RoomObject(
                id = RoomObjectIds.HEADPHONES,
                sceneReference =
                    RoomObjectSceneReference(
                        sceneEntityName = "PicoEarphone",
                        sceneNodeName = "SM_Picoearphone_001",
                    ),
                displayNameResourceId = R.string.item_name_earphones,
                descriptionResourceId = R.string.item_description_earphones,
                category = RoomObjectCategory.ELECTRONICS,
                movable = true,
            ),
            RoomObject(
                id = RoomObjectIds.ART_PRINT,
                sceneReference =
                    RoomObjectSceneReference(
                        sceneEntityName = "PicoPainting",
                        sceneNodeName = "SM_PicoPainting_001",
                    ),
                displayNameResourceId = R.string.item_name_painting,
                descriptionResourceId = R.string.item_description_painting,
                category = RoomObjectCategory.DECOR,
                movable = true,
            ),
            RoomObject(
                id = RoomObjectIds.DESK_LAMP,
                sceneReference =
                    RoomObjectSceneReference(
                        sceneEntityName = "PicoDeskLamp",
                        sceneNodeName = "SM_Picodesklamp_001",
                    ),
                displayNameResourceId = R.string.item_name_lamp,
                descriptionResourceId = R.string.item_description_lamp,
                category = RoomObjectCategory.LIGHTING,
                movable = true,
            ),
        )

    private val objectsById = objects.associateBy(RoomObject::id)
    private val objectsBySceneEntityName = objects.associateBy { it.sceneReference.sceneEntityName }

    init {
        require(objectsById.size == objects.size) { "Room object IDs must be unique" }
        require(objectsBySceneEntityName.size == objects.size) {
            "Room object scene entity names must be unique"
        }
    }

    fun find(id: RoomObjectId): RoomObject? = objectsById[id]

    fun require(id: RoomObjectId): RoomObject =
        requireNotNull(find(id)) { "Unknown room object ID: $id" }

    fun findBySceneEntityName(sceneEntityName: String): RoomObject? =
        objectsBySceneEntityName[sceneEntityName]
}
