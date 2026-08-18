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
package com.pico.spatial.sample.welcomespace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pico.spatial.sample.welcomespace.STAGE_ROOM_ID
import com.pico.spatial.sample.welcomespace.ui.decorate.DecorateSpacePage
import com.pico.spatial.sample.welcomespace.ui.furniture.FurnitureLibraryPage
import com.pico.spatial.sample.welcomespace.ui.home.HomePage
import com.pico.spatial.ui.platform.containers.LocalSpatialNavigator
import com.pico.spatial.ui.platform.containers.StageStyle
import com.pico.spatial.ui.platform.containers.closeStage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val coroutine = rememberCoroutineScope()
    val navController = rememberNavController()
    val spatialNavigator = LocalSpatialNavigator.current
    CompositionLocalProvider(LocalMainNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NAV_ROUTE_HOME,
            modifier = modifier
        ) {
            composable(NAV_ROUTE_HOME) {
                HomePage(
                    onNavToFurnitureLibrary = {
                        navController.navigate(NAV_ROUTE_FURNITURE_LIBRARY)
                    },
                    onNavToDecorateSpace = {
                        navController.navigate(NAV_ROUTE_DECORATE_SPACE)
                        coroutine.launch {
                            spatialNavigator.openStage(id = STAGE_ROOM_ID, style = StageStyle.Full)
                        }
                    }
                )
            }
            composable(NAV_ROUTE_FURNITURE_LIBRARY) { FurnitureLibraryPage() }
            composable(NAV_ROUTE_DECORATE_SPACE) {
                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (
                            event == Lifecycle.Event.ON_PAUSE || event == Lifecycle.Event.ON_DESTROY
                        ) {
                            coroutine.launch(Dispatchers.Main.immediate) {
                                closeStage()
                                navController.popBackStack(NAV_ROUTE_HOME, false)
                            }
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
                }
                DecorateSpacePage()
            }
        }
    }
}

// CompositionLocal to provide NavController globally in this scope
val LocalMainNavController =
    compositionLocalOf<NavHostController> { error("NavController not provided") }

private const val NAV_ROUTE_HOME = "homepage"
private const val NAV_ROUTE_FURNITURE_LIBRARY = "furniture_library"
private const val NAV_ROUTE_DECORATE_SPACE = "decorate_space"
