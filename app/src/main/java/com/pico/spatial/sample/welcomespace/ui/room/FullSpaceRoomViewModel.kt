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
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.ModelComponent
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.ecs.resource.ResourceLoadingException
import com.pico.spatial.core.ecs.resource.ShaderGraphMaterial
import com.pico.spatial.core.math.EulerAngles
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.sample.welcomespace.data.SCENE_ROOM
import com.pico.spatial.sample.welcomespace.data.assetBundle
import com.pico.spatial.sample.welcomespace.data.modelCards
import com.pico.spatial.sample.welcomespace.di.FurnitureLibraryScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinScopeComponent

class FullSpaceRoomViewModel : ViewModel(), KoinScopeComponent by FurnitureLibraryScope() {
    private val itemsToAdd = mutableMapOf<String, Entity>()

    val room =
        viewModelScope.async {
            try {
                    withContext(Dispatchers.IO) { assetBundle.await().loadModel(SCENE_ROOM) }
                } catch (e: ResourceLoadingException) {
                    Log.e(TAG, "Failed to load scene [$SCENE_ROOM] from bundle: ${e.message}")
                    null
                }
                ?.apply {
                    components[TransformComponent::class.java]?.apply {
                        setPosition(ROOM_INITIAL_POSITION)
                        setEulerAngles(ROOM_INITIAL_ROTATION)
                    }
                    findTargetItems()
                    hideTargetItems()
                }
        }

    fun showTargetItem(modelName: String) {
        itemsToAdd[modelName]?.apply {
            toggleFresnelEffect(true, this)
            enabled = true
        }
    }

    private fun Entity.findTargetItems() {
        modelCards.forEach { model ->
            // Finds the entity that has a ModelComponent (mesh and material) by name
            requireNotNull(findEntity(model.targetItemNodeName)?.getChildren()?.firstOrNull()) {
                    "Failed to find entity with mesh and material: ${model.targetItemNodeName}"
                }
                .apply {
                    // Add the entity to the map
                    itemsToAdd[model.targetItemSceneName] = this
                }
        }
    }

    private fun hideTargetItems() {
        itemsToAdd.values.forEach { entity ->
            toggleFresnelEffect(false, entity)
            entity.enabled = false
        }
    }

    private fun toggleFresnelEffect(playFresnel: Boolean, entity: Entity) {
        (entity.components[ModelComponent::class.java]?.materials?.get(0) as? ShaderGraphMaterial)
            ?.apply {
                toGlobal()
                if (playFresnel) {
                    setParameter(SHADER_GRAPH_PARAMETER_NAME, 1f)
                    // After a certain amount of time, remove the fresnel effect
                    viewModelScope.launch {
                        delay(FRESNEL_EFFECT_TIME)
                        setParameter(SHADER_GRAPH_PARAMETER_NAME, 0f)
                        close()
                    }
                } else {
                    setParameter(SHADER_GRAPH_PARAMETER_NAME, 0f)
                }
            }
    }

    companion object {
        private const val TAG = "RoomScene"
        private val ROOM_INITIAL_POSITION = Vector3(0.15f, 0f, -3.6f)
        private val ROOM_INITIAL_ROTATION = EulerAngles(0f, -30f, 0f)
        const val FRESNEL_EFFECT_TIME = 15000L
    }
}

private const val SHADER_GRAPH_PARAMETER_NAME = "fresnel_effect_value"
