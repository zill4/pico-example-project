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
package com.pico.spatial.sample.welcomespace.ui.furniture

import androidx.lifecycle.ViewModel
import com.pico.spatial.sample.welcomespace.di.FurnitureLibraryScope
import com.pico.spatial.sample.welcomespace.ui.common.ItemSelector
import com.pico.spatial.sample.welcomespace.ui.common.ItemSelectorImpl
import org.koin.core.component.KoinScopeComponent

class FurnitureLibraryViewModel :
    ViewModel(), KoinScopeComponent by FurnitureLibraryScope(), ItemSelector by ItemSelectorImpl() {
    override fun onCleared() {
        super.onCleared()
        deselectAll()
        scope.close()
    }
}
