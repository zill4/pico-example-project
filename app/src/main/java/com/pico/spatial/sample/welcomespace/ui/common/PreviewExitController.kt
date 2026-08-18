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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.android.awaitFrame

@Composable
fun rememberPreviewExitController(onExit: suspend () -> Unit): PreviewExitController {
    var previewsEnabled by remember { mutableStateOf(true) }
    var shouldExit by remember { mutableStateOf(false) }

    LaunchedEffect(shouldExit) {
        if (shouldExit) {
            previewsEnabled = false
            awaitFrame()
            onExit()
        }
    }

    return remember(previewsEnabled, shouldExit) {
        PreviewExitController(
            previewsEnabled = previewsEnabled,
            requestExit = { if (!shouldExit) shouldExit = true },
        )
    }
}

data class PreviewExitController(
    val previewsEnabled: Boolean,
    val requestExit: () -> Unit,
)
