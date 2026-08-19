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
package com.pico.spatial.sample.welcomespace.ui.decorate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pico.spatial.sample.welcomespace.R
import com.pico.spatial.sample.welcomespace.data.modelCards
import com.pico.spatial.sample.welcomespace.di.DECORATE_SPACE_SCOPE_ID
import com.pico.spatial.sample.welcomespace.ui.common.ItemInteractionMode
import com.pico.spatial.sample.welcomespace.ui.common.ItemsLayout
import com.pico.spatial.sample.welcomespace.ui.common.NavTitleBar
import com.pico.spatial.sample.welcomespace.ui.common.rememberPreviewExitController
import com.pico.spatial.sample.welcomespace.ui.navigation.LocalMainNavController
import com.pico.spatial.ui.platform.containers.closeStage
import kotlinx.coroutines.android.awaitFrame
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun DecorateSpacePage(
    viewModel: DecorateSpaceViewModel =
        koinViewModel(
            scope =
                getKoin().getOrCreateScope(DECORATE_SPACE_SCOPE_ID, named(DECORATE_SPACE_SCOPE_ID))
        )
) {
    val navController = LocalMainNavController.current
    val sessionState by viewModel.sessionState.collectAsStateWithLifecycle()
    val previewExitController = rememberPreviewExitController {
        closeStage()
        awaitFrame()
        navController.popBackStack()
    }

    Box(
        modifier =
            Modifier.fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(R.color.badge_background))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NavTitleBar(
                mode = ItemInteractionMode.ADD_TO_SPACE,
                onBack = previewExitController.requestExit,
                onResetRoom = viewModel::resetRoom,
            )
            Spacer(modifier = Modifier.height(8.dp))
            ItemsLayout(
                mode = ItemInteractionMode.ADD_TO_SPACE,
                items = modelCards,
                itemStates = sessionState.objectStates,
                previewsEnabled = previewExitController.previewsEnabled,
                onSelect = { objectId, _ -> viewModel.placeTargetItem(objectId) },
            )
        }
    }
}
