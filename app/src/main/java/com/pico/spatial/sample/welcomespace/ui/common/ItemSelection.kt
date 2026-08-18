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

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.pico.spatial.sample.welcomespace.data.modelCards

/** Interface for managing item selection state. */
interface ItemSelector {
    val selectionMap: SnapshotStateMap<String, Boolean>

    fun select(name: String)

    fun deselect(name: String)

    fun deselectAll()
}

/** Implementation of [ItemSelector] using [SnapshotStateMap] for Compose state tracking. */
class ItemSelectorImpl : ItemSelector {
    override val selectionMap =
        mutableStateMapOf<String, Boolean>().apply {
            putAll(modelCards.associate { it.targetItemSceneName to false })
        }

    override fun select(name: String) {
        selectionMap[name] = true
    }

    override fun deselect(name: String) {
        selectionMap[name] = false
    }

    override fun deselectAll() {
        selectionMap.forEach { (name, _) -> deselect(name) }
    }
}
