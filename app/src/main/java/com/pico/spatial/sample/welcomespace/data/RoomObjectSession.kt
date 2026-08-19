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

data class SceneSnapshot(val objectIds: Set<RoomObjectId>)

fun interface PerceptionProvider {
    fun currentSnapshot(): SceneSnapshot
}

class VirtualScenePerceptionProvider(private val sceneObjectIds: () -> Set<RoomObjectId>) :
    PerceptionProvider {
    override fun currentSnapshot(): SceneSnapshot = SceneSnapshot(sceneObjectIds())
}

enum class RoomObjectStatus {
    AVAILABLE,
    IN_ROOM,
    SELECTED,
    UNAVAILABLE,
}

data class RoomSessionState(val objectStates: Map<RoomObjectId, RoomObjectStatus>) {
    val selectedObjectId: RoomObjectId?
        get() = objectStates.entries.firstOrNull { it.value == RoomObjectStatus.SELECTED }?.key
}

enum class RoomInteractionResult {
    APPLIED,
    DESELECTED,
    UNKNOWN_OBJECT,
    UNAVAILABLE_OBJECT,
    NOT_IN_ROOM,
}

class RoomObjectSession(objectIds: List<RoomObjectId>) {
    private val knownObjectIds = objectIds.toList()
    private var perceivedObjectIds = emptySet<RoomObjectId>()

    var state = RoomSessionState(knownObjectIds.associateWith { RoomObjectStatus.UNAVAILABLE })
        private set

    init {
        require(knownObjectIds.distinct().size == knownObjectIds.size) {
            "Room session object IDs must be unique"
        }
    }

    fun applySnapshot(snapshot: SceneSnapshot) {
        perceivedObjectIds = snapshot.objectIds.intersect(knownObjectIds.toSet())
        state =
            RoomSessionState(
                knownObjectIds.associateWith { id ->
                    if (id !in perceivedObjectIds) {
                        RoomObjectStatus.UNAVAILABLE
                    } else {
                        when (state.objectStates[id]) {
                            RoomObjectStatus.IN_ROOM -> RoomObjectStatus.IN_ROOM
                            RoomObjectStatus.SELECTED -> RoomObjectStatus.SELECTED
                            else -> RoomObjectStatus.AVAILABLE
                        }
                    }
                }
            )
    }

    fun place(id: RoomObjectId): RoomInteractionResult =
        when (val status = state.objectStates[id]) {
            null -> RoomInteractionResult.UNKNOWN_OBJECT
            RoomObjectStatus.UNAVAILABLE -> RoomInteractionResult.UNAVAILABLE_OBJECT
            RoomObjectStatus.AVAILABLE -> {
                updateStatus(id, RoomObjectStatus.IN_ROOM)
                RoomInteractionResult.APPLIED
            }
            RoomObjectStatus.IN_ROOM,
            RoomObjectStatus.SELECTED -> RoomInteractionResult.APPLIED
        }

    fun toggleSelection(id: RoomObjectId): RoomInteractionResult =
        when (state.objectStates[id]) {
            null -> RoomInteractionResult.UNKNOWN_OBJECT
            RoomObjectStatus.UNAVAILABLE -> RoomInteractionResult.UNAVAILABLE_OBJECT
            RoomObjectStatus.AVAILABLE -> RoomInteractionResult.NOT_IN_ROOM
            RoomObjectStatus.IN_ROOM -> {
                state =
                    RoomSessionState(
                        state.objectStates.mapValues { (candidateId, status) ->
                            when {
                                candidateId == id -> RoomObjectStatus.SELECTED
                                status == RoomObjectStatus.SELECTED -> RoomObjectStatus.IN_ROOM
                                else -> status
                            }
                        }
                    )
                RoomInteractionResult.APPLIED
            }
            RoomObjectStatus.SELECTED -> {
                updateStatus(id, RoomObjectStatus.IN_ROOM)
                RoomInteractionResult.DESELECTED
            }
        }

    fun reset() {
        state =
            RoomSessionState(
                knownObjectIds.associateWith { id ->
                    if (id in perceivedObjectIds) RoomObjectStatus.AVAILABLE
                    else RoomObjectStatus.UNAVAILABLE
                }
            )
    }

    private fun updateStatus(id: RoomObjectId, status: RoomObjectStatus) {
        state = RoomSessionState(state.objectStates + (id to status))
    }
}
