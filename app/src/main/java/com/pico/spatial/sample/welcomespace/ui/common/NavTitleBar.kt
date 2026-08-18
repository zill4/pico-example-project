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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import com.pico.spatial.sample.welcomespace.R
import com.pico.spatial.sample.welcomespace.ui.navigation.LocalMainNavController
import com.pico.spatial.ui.design.Icon
import com.pico.spatial.ui.design.IconButton
import com.pico.spatial.ui.design.IconButtonDefaults
import com.pico.spatial.ui.design.PicoTheme.colorScheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.design.TitleAlignment
import com.pico.spatial.ui.design.TitleBar
import com.pico.spatial.ui.foundation.vibrant.Vibrant
import com.pico.spatial.ui.foundation.vibrant.withVibrant
import com.pico.spatial.ui.graphics.Vibrant

@Composable
fun NavTitleBar(mode: ItemInteractionMode, onBack: (() -> Unit)? = null) {
    val navController = LocalMainNavController.current
    TitleBar(
        modifier = Modifier.size(1280.dp, 96.dp),
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text =
                        if (mode == ItemInteractionMode.CHECK_DETAIL)
                            stringResource(R.string.furniture_library_title)
                        else stringResource(R.string.decorate_space_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 30.sp,
                )
                Text(
                    text =
                        if (mode == ItemInteractionMode.CHECK_DETAIL)
                            stringResource(R.string.furniture_library_subtitle)
                        else stringResource(R.string.decorate_space_subtitle),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 16.sp,
                    color = Color.Vibrant.withVibrant(Vibrant.Semidark),
                )
            }
        },
        leadingActions = {
            IconButton(
                modifier =
                    Modifier.size(48.dp).semantics { contentDescription = "nav_back_button" },
                onClick = dropUnlessResumed { onBack?.invoke() ?: navController.popBackStack() },
                colors =
                    IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = colorScheme.fillPrimary
                    )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_nav_back),
                    contentDescription = stringResource(R.string.nav_back),
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        titleAlignment = TitleAlignment.CenterInBar
    )
}
