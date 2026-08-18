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
package com.pico.spatial.sample.welcomespace.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pico.spatial.sample.welcomespace.R
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.ButtonColors
import com.pico.spatial.ui.design.ButtonDefaults
import com.pico.spatial.ui.design.PicoTheme.colorScheme
import com.pico.spatial.ui.design.PicoTheme.typography
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.foundation.material.backgroundMaterial
import com.pico.spatial.ui.platform.Material

@Composable
fun HomePage(onNavToFurnitureLibrary: () -> Unit, onNavToDecorateSpace: () -> Unit) {
    // Use image as background
    val imageBitmap = ImageBitmap.imageResource(R.drawable.homepage_background)
    val imageBrush = remember(imageBitmap) { ShaderBrush(ImageShader(imageBitmap)) }
    Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(imageBrush)) {
        Row(
            modifier = Modifier.padding(top = 500.dp, start = 50.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Title and description
            Column(modifier = Modifier.width(634.dp)) {
                Text(
                    text = stringResource(R.string.homepage_title),
                    fontSize = 40.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 44.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = stringResource(id = R.string.homepage_description),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    lineHeight = 18.sp,
                    color = Color.White.copy(alpha = 0.4f)
                )
            }
            Spacer(modifier = Modifier.size(178.dp))
            // Buttons for browsing furniture and entering the room
            Row(modifier = Modifier.size(380.dp, 56.dp)) {
                CustomButton(
                    semanticsText = "browse_furniture_button",
                    text = stringResource(R.string.homepage_button_browse_furniture),
                    buttonColors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = colorScheme.labelPrimaryLight
                        )
                ) {
                    onNavToFurnitureLibrary()
                }
                Spacer(modifier = Modifier.size(20.dp))
                CustomButton(
                    semanticsText = "enter_room_button",
                    text = stringResource(R.string.homepage_button_enter_room),
                ) {
                    onNavToDecorateSpace()
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    semanticsText: String,
    text: String,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    Button(
        modifier =
            Modifier.semantics { contentDescription = semanticsText }
                .size(180.dp, 56.dp)
                .clip(RoundedCornerShape(28.dp))
                .backgroundMaterial(true, Material.Thin),
        colors = buttonColors,
        onClick = onClick,
    ) {
        Text(
            text = text,
            fontStyle = typography.titleMedium.fontStyle,
        )
    }
}
