package com.pico.spatial.sample.welcomespace.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class RoomObjectCatalogTest {
    @Test
    fun catalogExposesStableProductIdsAndSceneBindings() {
        assertEquals(
            listOf("xr_headset", "vase", "headphones", "art_print", "desk_lamp"),
            RoomObjectCatalog.objects.map { it.id.value },
        )

        val headset = RoomObjectCatalog.require(RoomObjectIds.XR_HEADSET)
        assertEquals("PicoEquipment", headset.sceneReference.sceneEntityName)
        assertEquals("SM_Picoequipment_001", headset.sceneReference.sceneNodeName)
        assertEquals(RoomObjectCategory.ELECTRONICS, headset.category)
        assertTrue(headset.movable)
    }

    @Test
    fun invalidSemanticIdIsRejected() {
        assertThrows(IllegalArgumentException::class.java) { RoomObjectId("Pico Equipment") }
    }

    @Test
    fun unknownCatalogIdIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            RoomObjectCatalog.require(RoomObjectId("unknown_object"))
        }
    }
}
