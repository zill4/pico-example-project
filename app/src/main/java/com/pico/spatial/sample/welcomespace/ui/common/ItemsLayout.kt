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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.pico.spatial.sample.welcomespace.data.ModelCard
import com.pico.spatial.sample.welcomespace.data.RoomObjectId
import com.pico.spatial.sample.welcomespace.data.RoomObjectStatus

@Composable
fun ItemsLayout(
    mode: ItemInteractionMode,
    items: List<ModelCard>,
    itemStates: Map<RoomObjectId, RoomObjectStatus>,
    previewsEnabled: Boolean = true,
    onSelect: (objectId: RoomObjectId, title: String) -> Unit,
) {
    Row(modifier = Modifier.size(1216.dp, 572.dp)) {
        val leftSingleItem = items.first()
        ItemModelCard(
            mode = mode,
            cardSize = DpSize(586.dp, 572.dp),
            modelDepth = 440.dp,
            modelSectionHeight = 500.dp,
            buttonSize = DpSize(104.dp, 48.dp),
            buttonCornerRadius = 24.dp,
            buttonStartPadding = 12.dp,
            buttonEndPadding = 6.dp,
            iconSize =
                if (mode == ItemInteractionMode.CHECK_DETAIL) DpSize(24.dp, 24.dp)
                else DpSize(20.dp, 20.dp),
            modelBoundingBoxSize = leftSingleItem.modelBoundingBoxSize,
            objectId = leftSingleItem.objectId,
            modelName = leftSingleItem.targetItemSceneName,
            title = stringResource(leftSingleItem.titleResourceId),
            description = stringResource(leftSingleItem.descriptionResourceId),
            itemStatus = itemStates[leftSingleItem.objectId] ?: RoomObjectStatus.UNAVAILABLE,
            previewEnabled = previewsEnabled,
            onSelect = onSelect,
        )
        Spacer(modifier = Modifier.size(30.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.size(600.dp, 572.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            items(items.drop(1), key = { it.objectId.value }) { item ->
                ItemModelCard(
                    mode = mode,
                    modelBoundingBoxSize = item.modelBoundingBoxSize,
                    objectId = item.objectId,
                    modelName = item.targetItemSceneName,
                    title = stringResource(item.titleResourceId),
                    description = stringResource(item.descriptionResourceId),
                    itemStatus = itemStates[item.objectId] ?: RoomObjectStatus.UNAVAILABLE,
                    previewEnabled = previewsEnabled,
                    onSelect = onSelect,
                )
            }
        }
    }
}
