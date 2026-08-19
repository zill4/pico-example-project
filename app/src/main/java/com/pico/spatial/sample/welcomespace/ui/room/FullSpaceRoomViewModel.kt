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
package com.pico.spatial.sample.welcomespace.ui.room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pico.spatial.core.ecs.CollisionComponent
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.HoverEffectComponent
import com.pico.spatial.core.ecs.InteractableComponent
import com.pico.spatial.core.ecs.ModelComponent
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.ecs.resource.PhysicsMaterialResource
import com.pico.spatial.core.ecs.resource.ResourceLoadingException
import com.pico.spatial.core.ecs.resource.ShaderGraphMaterial
import com.pico.spatial.core.ecs.resource.ShapeResource
import com.pico.spatial.core.math.EulerAngles
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.sample.welcomespace.data.PerceptionProvider
import com.pico.spatial.sample.welcomespace.data.RoomInteractionResult
import com.pico.spatial.sample.welcomespace.data.RoomObjectCatalog
import com.pico.spatial.sample.welcomespace.data.RoomObjectId
import com.pico.spatial.sample.welcomespace.data.RoomObjectSession
import com.pico.spatial.sample.welcomespace.data.RoomSessionState
import com.pico.spatial.sample.welcomespace.data.SCENE_ROOM
import com.pico.spatial.sample.welcomespace.data.VirtualScenePerceptionProvider
import com.pico.spatial.sample.welcomespace.data.assetBundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class FullSpaceRoomViewModel : ViewModel() {
    private val session = RoomObjectSession(RoomObjectCatalog.objects.map { it.id })
    private val itemsToAdd = mutableMapOf<RoomObjectId, Entity>()
    private val objectIdsByEntityUuid = mutableMapOf<Long, RoomObjectId>()
    private val selectionFeedbackMaterials = mutableMapOf<RoomObjectId, ShaderGraphMaterial>()
    private var roomEntity: Entity? = null

    private val _sessionState = MutableStateFlow(session.state)
    val sessionState: StateFlow<RoomSessionState> = _sessionState.asStateFlow()

    val room =
        viewModelScope.async {
            try {
                    withContext(Dispatchers.IO) { assetBundle.await().loadModel(SCENE_ROOM) }
                } catch (e: ResourceLoadingException) {
                    Log.e(TAG, "Failed to load scene [$SCENE_ROOM] from bundle: ${e.message}")
                    null
                }
                ?.apply {
                    roomEntity = this
                    components[TransformComponent::class.java]?.apply {
                        setPosition(ROOM_INITIAL_POSITION)
                        setEulerAngles(ROOM_INITIAL_ROTATION)
                    }
                    findTargetItems()
                    hideTargetItems()
                    val perceptionProvider: PerceptionProvider = VirtualScenePerceptionProvider {
                        itemsToAdd.keys.toSet()
                    }
                    session.applySnapshot(perceptionProvider.currentSnapshot())
                    publishSessionState()
                    Log.i(TAG, "PM-1 scene ready: ${itemsToAdd.size} room objects available")
                }
        }

    fun placeTargetItem(objectId: RoomObjectId): RoomInteractionResult {
        val result = session.place(objectId)
        if (result == RoomInteractionResult.APPLIED) {
            itemsToAdd[objectId]?.enabled = true
        }
        publishSessionState()
        Log.i(TAG, "PM-1 place [$objectId]: $result")
        return result
    }

    fun handleSpatialTap(entity: Entity?): RoomInteractionResult {
        if (entity == null) {
            Log.w(TAG, "PM-1 tap target missing")
            return RoomInteractionResult.UNKNOWN_OBJECT
        }
        val objectId =
            objectIdsByEntityUuid[entity.getUUID()]
                ?: run {
                    Log.w(TAG, "PM-1 tap target unknown: ${entity.getName()} (${entity.getUUID()})")
                    return RoomInteractionResult.UNKNOWN_OBJECT
                }
        val result = session.toggleSelection(objectId)
        syncSelectionFeedback()
        publishSessionState()
        Log.i(TAG, "PM-1 tap [$objectId]: $result")
        return result
    }

    fun resetRoom() {
        session.reset()
        clearSelectionFeedback()
        hideTargetItems()
        publishSessionState()
        Log.i(TAG, "PM-1 room reset")
    }

    private fun Entity.findTargetItems() {
        RoomObjectCatalog.objects.forEach { roomObject ->
            val modelEntity =
                findEntity(roomObject.sceneReference.sceneNodeName)?.findFirstModelEntity()
            if (modelEntity == null) {
                Log.w(
                    TAG,
                    "Room object unavailable [${roomObject.id}]: node ${roomObject.sceneReference.sceneNodeName}",
                )
                return@forEach
            }

            if (!modelEntity.configureInteraction()) {
                Log.w(TAG, "Room object unavailable [${roomObject.id}]: invalid visual bounds")
                return@forEach
            }

            itemsToAdd[roomObject.id] = modelEntity
            objectIdsByEntityUuid[modelEntity.getUUID()] = roomObject.id
        }
    }

    private fun Entity.findFirstModelEntity(): Entity? {
        if (components[ModelComponent::class.java] != null) return this
        return getChildren().firstNotNullOfOrNull { child -> child.findFirstModelEntity() }
    }

    private fun Entity.configureInteraction(): Boolean {
        val size = getVisualBounds(this, recursive = true, enabledOnly = false).size
        if (
            size.x <= MIN_COLLIDER_SIZE ||
                size.y <= MIN_COLLIDER_SIZE ||
                size.z <= MIN_COLLIDER_SIZE
        ) {
            return false
        }

        components.set(
            CollisionComponent(
                collisionShape = listOf(ShapeResource.createBox(size)),
                physicsMaterial = PhysicsMaterialResource(),
            )
        )
        components.set(InteractableComponent())
        components.set(HoverEffectComponent())
        return true
    }

    private fun syncSelectionFeedback() {
        clearSelectionFeedback()
        val selectedObjectId = session.state.selectedObjectId ?: return
        val entity = itemsToAdd[selectedObjectId] ?: return
        val material =
            entity.components[ModelComponent::class.java]?.materials?.firstOrNull()
                as? ShaderGraphMaterial ?: return
        material.toGlobal()
        material.setParameter(SHADER_GRAPH_PARAMETER_NAME, 1f)
        selectionFeedbackMaterials[selectedObjectId] = material
    }

    private fun clearSelectionFeedback() {
        selectionFeedbackMaterials.values.forEach { material ->
            material.setParameter(SHADER_GRAPH_PARAMETER_NAME, 0f)
            material.close()
        }
        selectionFeedbackMaterials.clear()
    }

    private fun publishSessionState() {
        _sessionState.value = session.state
    }

    private fun clearRuntimeInteractionComponents() {
        itemsToAdd.values.forEach { entity ->
            entity.components.remove(HoverEffectComponent::class.java)
            entity.components.remove(InteractableComponent::class.java)
            entity.components.remove(CollisionComponent::class.java)
            entity.enabled = false
        }
    }

    override fun onCleared() {
        clearSelectionFeedback()
        clearRuntimeInteractionComponents()
        itemsToAdd.clear()
        objectIdsByEntityUuid.clear()
        room.cancel()
        roomEntity?.destroy()
        roomEntity = null
        super.onCleared()
    }

    private fun hideTargetItems() {
        itemsToAdd.values.forEach { entity -> entity.enabled = false }
    }

    companion object {
        private const val TAG = "RoomScene"
        private const val MIN_COLLIDER_SIZE = 0.001f
        private val ROOM_INITIAL_POSITION = Vector3(0.15f, 0f, -3.6f)
        private val ROOM_INITIAL_ROTATION = EulerAngles(0f, -30f, 0f)
    }
}

private const val SHADER_GRAPH_PARAMETER_NAME = "fresnel_effect_value"
