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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pico.spatial.sample.welcomespace.R
import com.pico.spatial.sample.welcomespace.data.RoomObjectId
import com.pico.spatial.sample.welcomespace.data.RoomObjectStatus
import com.pico.spatial.sample.welcomespace.data.assetBundle
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.ButtonDefaults
import com.pico.spatial.ui.design.Icon
import com.pico.spatial.ui.design.PicoTheme.colorScheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.foundation.content.Model
import com.pico.spatial.ui.foundation.content.ModelLoadingState
import com.pico.spatial.ui.foundation.content.Resizability
import com.pico.spatial.ui.foundation.content.Source
import com.pico.spatial.ui.foundation.content.SpatialModelView
import com.pico.spatial.ui.foundation.effect3d.rotate3D
import com.pico.spatial.ui.foundation.gesture.detectSpatialDragGesture
import com.pico.spatial.ui.foundation.layout.requiredDepth
import com.pico.spatial.ui.foundation.vibrant.Vibrant
import com.pico.spatial.ui.foundation.vibrant.vibrantEffect
import com.pico.spatial.ui.foundation.vibrant.withVibrant
import com.pico.spatial.ui.geometry.Offset3D
import com.pico.spatial.ui.graphics.Vibrant
import kotlinx.coroutines.runBlocking

@Composable
fun ItemModelCard(
    mode: ItemInteractionMode,
    cardSize: DpSize = DpSize(292.dp, 272.dp),
    modelDepth: Dp = 200.dp,
    modelSectionHeight: Dp = 200.dp,
    buttonSize: DpSize = DpSize(80.dp, 36.dp),
    buttonCornerRadius: Dp = 18.dp,
    buttonStartPadding: Dp = 10.dp,
    buttonEndPadding: Dp = 5.dp,
    iconSize: DpSize =
        if (mode == ItemInteractionMode.CHECK_DETAIL) DpSize(18.dp, 18.dp)
        else DpSize(16.dp, 16.dp),
    modelBoundingBoxSize: DpSize,
    objectId: RoomObjectId,
    modelName: String,
    title: String,
    description: String,
    itemStatus: RoomObjectStatus = RoomObjectStatus.AVAILABLE,
    previewEnabled: Boolean = true,
    onSelect: (objectId: RoomObjectId, title: String) -> Unit,
) {
    val context = LocalContext.current
    var dragAmount by remember { mutableStateOf(Offset3D.Zero) }
    var shouldClearDrag by remember { mutableStateOf(false) }

    Column(modifier = Modifier.size(cardSize)) {
        // Model preview section
        Box(
            modifier =
                Modifier.size(cardSize.width, modelSectionHeight)
                    .clip(RoundedCornerShape(8.dp))
                    .vibrantEffect(Vibrant.Neutral)
                    .background(Color.Vibrant),
            contentAlignment = Alignment.Center
        ) {
            if (previewEnabled) {
                DisposableEffect(modelName, previewEnabled) {
                    onDispose {
                        dragAmount = Offset3D.Zero
                        shouldClearDrag = false
                    }
                }
                SpatialModelView(
                    source = Source.bundle(modelName) { runBlocking { assetBundle.await() } },
                    resizability = Resizability.FitInside,
                    modifier =
                        Modifier.size(modelBoundingBoxSize)
                            .requiredDepth(modelDepth)
                            .clip(RoundedCornerShape(8.dp))
                            .alpha(
                                when (itemStatus) {
                                    RoomObjectStatus.AVAILABLE -> 1f
                                    RoomObjectStatus.UNAVAILABLE -> 0.35f
                                    RoomObjectStatus.IN_ROOM,
                                    RoomObjectStatus.SELECTED -> 0.6f
                                }
                            )
                            .pointerInput(Unit) {
                                if (itemStatus == RoomObjectStatus.AVAILABLE) {
                                    detectSpatialDragGesture(
                                        context = context,
                                        onDragEnd = { shouldClearDrag = true },
                                        onDrag = { spatialDragValue ->
                                            val deltaDrag = spatialDragValue.dragAmount
                                            val newDragAmount = dragAmount + deltaDrag
                                            if (withInRotationLimit(newDragAmount)) {
                                                dragAmount = newDragAmount
                                            }
                                        }
                                    )
                                }
                            }
                            .rotate3D {
                                if (!shouldClearDrag && itemStatus == RoomObjectStatus.AVAILABLE) {
                                    dragAmount.toRotation3D(SENSITIVITY)
                                } else {
                                    Offset3D.Zero.toRotation3D(SENSITIVITY).apply {
                                        dragAmount = Offset3D.Zero
                                        shouldClearDrag = false
                                    }
                                }
                            }
                ) { state ->
                    if (state is ModelLoadingState.Success) {
                        Model(state.model)
                    }
                }
            }
        }
        Spacer(Modifier.size(17.dp))
        Row(
            modifier = Modifier.width(cardSize.width).padding(start = 4.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text section, including title and description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 16.sp,
                    color = Color.Vibrant.withVibrant(Vibrant.Semidark)
                )
            }
            // Button for viewing or adding an item
            Button(
                modifier =
                    Modifier.padding(start = buttonStartPadding, end = buttonEndPadding).semantics {
                        contentDescription =
                            "${if (mode == ItemInteractionMode.CHECK_DETAIL) "check_detail_button" else "add_to_space_button"}_${modelName}"
                    },
                size =
                    ButtonDefaults.buttonSize(width = buttonSize.width, height = buttonSize.height),
                shape = RoundedCornerShape(buttonCornerRadius),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Vibrant.withVibrant(Vibrant.Light),
                        contentColor = colorScheme.fillPrimary
                    ),
                onClick = { onSelect(objectId, title) },
                enabled = itemStatus == RoomObjectStatus.AVAILABLE,
            ) {
                if (itemStatus == RoomObjectStatus.AVAILABLE) {
                    Icon(
                        imageVector =
                            ImageVector.vectorResource(
                                if (mode == ItemInteractionMode.CHECK_DETAIL)
                                    R.drawable.ic_check_item
                                else R.drawable.ic_add_item
                            ),
                        contentDescription =
                            if (mode == ItemInteractionMode.CHECK_DETAIL)
                                stringResource(R.string.check_item)
                            else stringResource(R.string.add_item),
                        modifier = Modifier.size(iconSize)
                    )
                } else {
                    Text(
                        text =
                            if (mode == ItemInteractionMode.CHECK_DETAIL)
                                stringResource(R.string.item_state_viewing)
                            else
                                stringResource(
                                    when (itemStatus) {
                                        RoomObjectStatus.IN_ROOM -> R.string.item_state_in_room
                                        RoomObjectStatus.SELECTED -> R.string.item_state_selected
                                        RoomObjectStatus.UNAVAILABLE ->
                                            R.string.item_state_unavailable
                                        RoomObjectStatus.AVAILABLE -> R.string.add_item
                                    }
                                ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                    )
                }
            }
        }
    }
}

private const val SENSITIVITY = 0.05f
