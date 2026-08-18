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
package com.pico.spatial.sample.welcomespace.ecs

import com.pico.spatial.core.ecs.Component
import com.pico.spatial.core.ecs.EntityQueryCondition
import com.pico.spatial.core.ecs.SceneUpdateContext
import com.pico.spatial.core.ecs.System
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.math.EulerAngles

class RotationComponent(
    var isEnabled: Boolean = false,
    val step: Float = 1f // degrees per frame
) : Component() {
    // You need to override the clone method to make the custom component clonable
    override fun clone(): RotationComponent {
        return RotationComponent(this.isEnabled, this.step)
    }
}

class RotationSystem : System() {
    override fun update(context: SceneUpdateContext) {
        val condition = EntityQueryCondition.hasComponent(RotationComponent::class.java)
        val filteredEntities = context.scene.queryEntity(condition)
        filteredEntities.forEach { entity ->
            val rotation = entity.components[RotationComponent::class.java]
            val transform = entity.components[TransformComponent::class.java]
            if (rotation?.isEnabled == true && transform != null) {
                // Incrementally rotate using quaternion multiplication
                val deltaQuaternion = EulerAngles(0f, rotation.step, 0f).toQuat()
                transform.quaternion *= deltaQuaternion
            }
        }
    }
}
