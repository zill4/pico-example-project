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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.ImageBasedLightSource
import com.pico.spatial.core.ecs.LoadType
import com.pico.spatial.core.ecs.StageEnvironmentLightingComponent
import com.pico.spatial.core.ecs.resource.TextureResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/** ViewModel responsible for providing the Image-Based Light (IBL) entity for the room. */
class IblViewModel : ViewModel() {
    val entity = viewModelScope.async { IblEntity().apply { initialize() } }
}

/** Entity that encapsulates the environment lighting components. */
class IblEntity : Entity() {
    suspend fun initialize() {
        val iblTexture =
            withContext(Dispatchers.IO) {
                TextureResource.load(
                    path = IBL_PATH,
                    loadType = LoadType.FROM_ASSETS,
                )
            }
        iblTexture.use {
            val iblSource = ImageBasedLightSource.Single(it)
            val iblComponent = StageEnvironmentLightingComponent(iblSource, INTENSITY_EXPONENT)
            components.set(iblComponent)
        }
    }

    companion object {
        private const val IBL_PATH = "ibl.ktx"
        private const val INTENSITY_EXPONENT = 8f
    }
}
