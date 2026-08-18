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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pico.spatial.sample.welcomespace.di.DECORATE_SPACE_SCOPE_ID
import com.pico.spatial.ui.foundation.content.SpatialView
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun FullSpaceRoom(
    iblViewModel: IblViewModel = viewModel(),
    roomViewModel: FullSpaceRoomViewModel =
        koinViewModel(
            scope =
                getKoin().getOrCreateScope(DECORATE_SPACE_SCOPE_ID, named(DECORATE_SPACE_SCOPE_ID))
        )
) {
    val iblEntityDeferred = remember { iblViewModel.entity }
    SpatialView(
        initial = { content, _ ->
            val iblEntity = iblEntityDeferred.await()
            content.addEntity(iblEntity)
            roomViewModel.room.await()?.let { content.addEntity(it) }
        }
    )
}
