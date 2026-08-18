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
package com.pico.spatial.sample.welcomespace.di

import com.pico.spatial.sample.welcomespace.ui.decorate.DecorateSpaceViewModel
import com.pico.spatial.sample.welcomespace.ui.display.ItemDisplayViewModel
import com.pico.spatial.sample.welcomespace.ui.furniture.FurnitureLibraryViewModel
import com.pico.spatial.sample.welcomespace.ui.room.FullSpaceRoomViewModel
import org.koin.core.module.dsl.scopedOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val furnitureLibraryModule = module {
    scope(named(FURNITURE_LIBRARY_SCOPE_ID)) {
        scopedOf(::FurnitureLibraryViewModel)
        scopedOf(::ItemDisplayViewModel)
    }
}

val decorateSpaceModule = module {
    scope(named(DECORATE_SPACE_SCOPE_ID)) {
        scopedOf(::DecorateSpaceViewModel)
        scopedOf(::FullSpaceRoomViewModel)
    }
}

val appModules = listOf(furnitureLibraryModule, decorateSpaceModule)
