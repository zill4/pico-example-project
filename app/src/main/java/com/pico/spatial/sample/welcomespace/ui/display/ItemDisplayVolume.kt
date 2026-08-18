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
package com.pico.spatial.sample.welcomespace.ui.display

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pico.spatial.core.ecs.CollisionComponent
import com.pico.spatial.core.ecs.Entity
import com.pico.spatial.core.ecs.InteractableComponent
import com.pico.spatial.core.ecs.TransformComponent
import com.pico.spatial.core.ecs.ViewCoordinateSpace
import com.pico.spatial.core.ecs.resource.PhysicsMaterialResource
import com.pico.spatial.core.ecs.resource.ShapeResource
import com.pico.spatial.core.math.Transform
import com.pico.spatial.core.math.Vector3
import com.pico.spatial.sample.welcomespace.CROSS_CONTAINER_BUNDLE_MODEL_NAME
import com.pico.spatial.sample.welcomespace.CROSS_CONTAINER_BUNDLE_TITLE
import com.pico.spatial.sample.welcomespace.R
import com.pico.spatial.sample.welcomespace.data.assetBundle
import com.pico.spatial.sample.welcomespace.di.FURNITURE_LIBRARY_SCOPE_ID
import com.pico.spatial.sample.welcomespace.ecs.RotationComponent
import com.pico.spatial.sample.welcomespace.ecs.RotationSystem
import com.pico.spatial.sample.welcomespace.ui.common.toRotation3D
import com.pico.spatial.ui.design.Badge
import com.pico.spatial.ui.design.BadgeDefaults
import com.pico.spatial.ui.design.Icon
import com.pico.spatial.ui.design.IconButton
import com.pico.spatial.ui.design.IconButtonDefaults
import com.pico.spatial.ui.design.PicoTheme.colorScheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.design.ToggleIconButton
import com.pico.spatial.ui.design.ToggleIconButtonDefaults
import com.pico.spatial.ui.design.windows.Toolbar
import com.pico.spatial.ui.foundation.content.SpatialView
import com.pico.spatial.ui.foundation.dsl.registerSystem
import com.pico.spatial.ui.foundation.dsl.unregisterSystem
import com.pico.spatial.ui.foundation.gesture.detectSpatialDragGesture
import com.pico.spatial.ui.foundation.gesture.detectSpatialScaleGesture
import com.pico.spatial.ui.geometry.Offset3D
import com.pico.spatial.ui.platform.containers.WindowContainerScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun WindowContainerScope.ItemDisplayVolume(
    modifier: Modifier = Modifier,
    viewModel: ItemDisplayViewModel =
        koinViewModel(
            scope =
                getKoin()
                    .getOrCreateScope(FURNITURE_LIBRARY_SCOPE_ID, named(FURNITURE_LIBRARY_SCOPE_ID))
        )
) {
    val modelName = remember {
        bundle?.getString(CROSS_CONTAINER_BUNDLE_MODEL_NAME) ?: "Fail to pass in model name"
    }
    val title = remember {
        bundle?.getString(CROSS_CONTAINER_BUNDLE_TITLE) ?: "Fail to pass in title"
    }
    val initTransform = remember { Transform() }
    var item by remember { mutableStateOf<Entity?>(null) }
    var light by remember { mutableStateOf<Entity?>(null) }
    var nameTag by remember { mutableStateOf<Entity?>(null) }
    var isRotateEnabled by remember { mutableStateOf(false) }
    var isLightEnabled by remember { mutableStateOf(false) }
    var isNameTagEnabled by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var scale by remember { mutableFloatStateOf(1f) }
    var shouldUpdateScale by remember { mutableStateOf(false) }
    var dragAmount by remember { mutableStateOf(Offset3D.Zero) }
    var shouldClearDrag by remember { mutableStateOf(false) }
    var isScaleGestureDetected by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        registerSystem<RotationSystem>()
        onDispose {
            unregisterSystem<RotationSystem>()
            viewModel.finishDisplay(modelName)
        }
    }

    Box(modifier = modifier) {
        SpatialView(
            modifier =
                Modifier.pointerInput(Unit) {
                        if (!isScaleGestureDetected) {
                            detectSpatialDragGesture(
                                context = context,
                                onDragEnd = { shouldClearDrag = true },
                                onDrag = { spatialDragValue ->
                                    val newDragAmount = dragAmount + spatialDragValue.dragAmount
                                    dragAmount = newDragAmount
                                }
                            )
                        }
                    }
                    .pointerInput(Unit) {
                        detectSpatialScaleGesture(
                            context = context,
                            onScaleStart = { isScaleGestureDetected = true },
                            onScaleEnd = { isScaleGestureDetected = false },
                            onScale = { spatialScaleValue ->
                                val newScale = scale * spatialScaleValue.scaleValue
                                if (newScale in MIN_SCALE..MAX_SCALE) {
                                    scale = newScale
                                    shouldUpdateScale = true
                                }
                            }
                        )
                    },
            initial = { content, attachments ->
                item = withContext(Dispatchers.IO) { assetBundle.await().loadModel(modelName) }
                item?.apply {
                    components[TransformComponent::class.java]?.apply {
                        setPosition(initTransform.position)
                        setEulerAngles(initTransform.rotation)
                        setScaleVector(initTransform.scale)
                    }
                    components.set(RotationComponent(isEnabled = isRotateEnabled))
                    components.set(InteractableComponent())
                    components.set(
                        CollisionComponent(
                            collisionShape =
                                listOf(ShapeResource.createBox(size = INTERACTABLE_BOX_SIZE)),
                            physicsMaterial = PhysicsMaterialResource(),
                        )
                    )
                    light = findEntity(LIGHT_ENTITY_NAME)
                    content.addEntity(this)
                }
                nameTag =
                    attachments.entity(ATTACHMENT_ID)?.apply {
                        components.get<TransformComponent>()?.setPosition(NAME_TAG_POSITION)
                        content.addEntity(this)
                        enabled = isNameTagEnabled
                    }
            },
            update = { content, _ ->
                item?.components?.get(TransformComponent::class.java)?.apply {
                    if (!shouldClearDrag) {
                        val quaternion = dragAmount.toRotation3D(DRAG_SENSITIVITY).toQuaternion()
                        // Should convert the rotation from View's Global Coordinate Space to
                        // content's local Spatial Coordinate Space
                        val convertedRotation =
                            content.convertRotation(
                                rotation = quaternion,
                                from = ViewCoordinateSpace.Global,
                                to = content.localSpatialCoordinateSpace,
                            )
                        setQuaternion(convertedRotation)
                    } else {
                        dragAmount = Offset3D.Zero
                        shouldClearDrag = false
                    }
                    if (shouldUpdateScale) {
                        setScaleVector(Vector3(scale))
                        shouldUpdateScale = false
                    }
                }
            },
            attachments = {
                AttachmentPanel(id = ATTACHMENT_ID) {
                    Badge(
                        badgeColor =
                            BadgeDefaults.badgeColors(
                                backgroundColor = colorResource(R.color.badge_background),
                                contentColor = colorScheme.fillPrimary
                            ),
                        radius = 40.dp,
                        contentPadding = PaddingValues(horizontal = 44.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = title,
                            fontSize = 28.sp,
                            lineHeight = 36.sp,
                            fontWeight = FontWeight(700),
                        )
                    }
                }
            }
        )
        Toolbar(cornerSize = 55.dp) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CustomToggleIconButton(
                    semanticsText = "rotate_botton",
                    iconSize = 24.dp,
                    displayOption = DisplayOption.ROTATE,
                    checked = isRotateEnabled,
                    onCheckedChange = {
                        isRotateEnabled = it
                        item?.components?.get(RotationComponent::class.java)?.isEnabled = it
                    },
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomToggleIconButton(
                    semanticsText = "light_botton",
                    iconSize = 22.dp,
                    displayOption = DisplayOption.LIGHT,
                    checked = isLightEnabled,
                    onCheckedChange = {
                        isLightEnabled = it
                        light?.enabled = it
                    },
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomToggleIconButton(
                    semanticsText = "name-tag_botton",
                    iconSize = 20.dp,
                    displayOption = DisplayOption.NAME_TAG,
                    checked = isNameTagEnabled,
                    onCheckedChange = {
                        isNameTagEnabled = it
                        nameTag?.enabled = it
                    },
                )
                Spacer(modifier = Modifier.size(8.dp))
                CustomIconButton(
                    semanticsText = "reset_transform_botton",
                    displayOption = DisplayOption.RESET_TRANSFORM,
                    onClick = {
                        item?.components?.get(TransformComponent::class.java)?.apply {
                            setPosition(initTransform.position)
                            setEulerAngles(initTransform.rotation)
                            setScaleVector(initTransform.scale)
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun CustomToggleIconButton(
    semanticsText: String = "",
    iconSize: Dp,
    displayOption: DisplayOption,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ToggleIconButton(
        modifier = Modifier.size(52.dp).semantics { contentDescription = semanticsText },
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors =
            ToggleIconButtonDefaults.toggleIconButtonColors(
                checkedContainerColor = colorResource(R.color.on_checked_container),
                checkedContentColor = Color.Black,
                uncheckedContainerColor = Color.Transparent,
                uncheckedContentColor = Color.Black,
            ),
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = displayOption.iconRes),
            contentDescription = stringResource(id = displayOption.descriptionRes),
        )
    }
}

@Composable
fun CustomIconButton(
    semanticsText: String = "",
    displayOption: DisplayOption,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(52.dp).semantics { contentDescription = semanticsText },
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = colorScheme.fillPrimary
            ),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = displayOption.iconRes),
            contentDescription = stringResource(id = displayOption.descriptionRes),
        )
    }
}

enum class DisplayOption(
    @StringRes val descriptionRes: Int,
    @DrawableRes val iconRes: Int,
) {
    ROTATE(R.string.display_option_rotate, R.drawable.ic_display_option_rotate),
    LIGHT(R.string.display_option_light, R.drawable.ic_display_option_light),
    NAME_TAG(R.string.display_option_name_tag, R.drawable.ic_display_option_name_tag),
    RESET_TRANSFORM(
        R.string.display_option_reset_transform,
        R.drawable.ic_display_option_reset_transform
    );

    companion object {
        val all = listOf(ROTATE, LIGHT, NAME_TAG, RESET_TRANSFORM)
    }
}

private const val LIGHT_ENTITY_NAME = "Light"
private const val ATTACHMENT_ID = "name_tag"
private val NAME_TAG_POSITION = Vector3(0f, 0.28f, 0.28f)
private val INTERACTABLE_BOX_SIZE = Vector3(0.4f, 0.2f, 0.4f)
private const val DRAG_SENSITIVITY = 0.05f
private const val MIN_SCALE = 0.3f
private const val MAX_SCALE = 1.8f
