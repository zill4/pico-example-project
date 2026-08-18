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
package com.pico.spatial.sample.welcomespace.ui.common

import com.pico.spatial.core.math.Vector3
import com.pico.spatial.ui.foundation.geometry.NormalizedPoint3D
import com.pico.spatial.ui.foundation.geometry.Rotation3D
import com.pico.spatial.ui.foundation.geometry.RotationAxis3D
import com.pico.spatial.ui.geometry.Offset3D

/**
 * Extension for [Offset3D] to convert it to [Rotation3D] based on movement. This is used to map a
 * single-hand spatial drag gesture to a 3D rotation.
 */
fun Offset3D.toRotation3D(sensitivity: Float): Rotation3D {
    val delta = Vector3(x, y, z)
    val axis = delta.normalize()

    return Rotation3D(
        degree = delta.length() * sensitivity,
        axis = RotationAxis3D(-axis.y, axis.x, axis.z),
        pivot = NormalizedPoint3D.Center,
    )
}

/** Checks if the cumulative drag is within a reasonable rotation limit. */
fun withInRotationLimit(drag: Offset3D): Boolean {
    val dragVector = Vector3(drag.x, drag.y, drag.z)
    return dragVector.length() <= MAX_DRAG_LENGTH
}

private const val MAX_DRAG_LENGTH = 360f
