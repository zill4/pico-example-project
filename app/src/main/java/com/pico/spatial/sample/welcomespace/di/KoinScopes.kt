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

import org.koin.core.component.KoinScopeComponent
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class FurnitureLibraryScope : KoinScopeComponent {
    override val scope: Scope
        get() =
            getKoin()
                .getOrCreateScope(FURNITURE_LIBRARY_SCOPE_ID, named(FURNITURE_LIBRARY_SCOPE_ID))
}

class DecorateSpaceScope : KoinScopeComponent {
    override val scope: Scope
        get() = getKoin().getOrCreateScope(DECORATE_SPACE_SCOPE_ID, named(DECORATE_SPACE_SCOPE_ID))
}

const val FURNITURE_LIBRARY_SCOPE_ID = "furniture_library"
const val DECORATE_SPACE_SCOPE_ID = "decorate_space"
