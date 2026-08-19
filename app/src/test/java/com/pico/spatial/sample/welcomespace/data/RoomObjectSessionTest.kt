package com.pico.spatial.sample.welcomespace.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RoomObjectSessionTest {
    private val ids = listOf(RoomObjectIds.VASE, RoomObjectIds.HEADPHONES, RoomObjectIds.DESK_LAMP)

    @Test
    fun virtualSceneSnapshotControlsAvailability() {
        val provider = VirtualScenePerceptionProvider {
            setOf(RoomObjectIds.VASE, RoomObjectIds.HEADPHONES)
        }
        val session = RoomObjectSession(ids)

        session.applySnapshot(provider.currentSnapshot())

        assertEquals(RoomObjectStatus.AVAILABLE, session.state.objectStates[RoomObjectIds.VASE])
        assertEquals(
            RoomObjectStatus.AVAILABLE,
            session.state.objectStates[RoomObjectIds.HEADPHONES],
        )
        assertEquals(
            RoomObjectStatus.UNAVAILABLE,
            session.state.objectStates[RoomObjectIds.DESK_LAMP],
        )
    }

    @Test
    fun placedObjectCanBeSelectedAndDeselected() {
        val session = RoomObjectSession(ids)
        session.applySnapshot(SceneSnapshot(ids.toSet()))

        assertEquals(RoomInteractionResult.APPLIED, session.place(RoomObjectIds.VASE))
        assertEquals(RoomInteractionResult.APPLIED, session.toggleSelection(RoomObjectIds.VASE))
        assertEquals(RoomObjectIds.VASE, session.state.selectedObjectId)
        assertEquals(
            RoomInteractionResult.DESELECTED,
            session.toggleSelection(RoomObjectIds.VASE),
        )
        assertNull(session.state.selectedObjectId)
        assertEquals(RoomObjectStatus.IN_ROOM, session.state.objectStates[RoomObjectIds.VASE])
    }

    @Test
    fun selectingAnotherObjectDeselectsThePreviousObject() {
        val session = RoomObjectSession(ids)
        session.applySnapshot(SceneSnapshot(ids.toSet()))
        session.place(RoomObjectIds.VASE)
        session.place(RoomObjectIds.HEADPHONES)
        session.toggleSelection(RoomObjectIds.VASE)

        session.toggleSelection(RoomObjectIds.HEADPHONES)

        assertEquals(RoomObjectIds.HEADPHONES, session.state.selectedObjectId)
        assertEquals(RoomObjectStatus.IN_ROOM, session.state.objectStates[RoomObjectIds.VASE])
    }

    @Test
    fun unavailableAndUnknownObjectsFailSafely() {
        val session = RoomObjectSession(ids)
        session.applySnapshot(SceneSnapshot(setOf(RoomObjectIds.VASE)))

        assertEquals(
            RoomInteractionResult.UNAVAILABLE_OBJECT,
            session.place(RoomObjectIds.DESK_LAMP),
        )
        assertEquals(
            RoomInteractionResult.NOT_IN_ROOM,
            session.toggleSelection(RoomObjectIds.VASE),
        )
        assertEquals(
            RoomInteractionResult.UNKNOWN_OBJECT,
            session.place(RoomObjectId("unknown_object")),
        )
    }

    @Test
    fun resetRestoresOriginalVisibleAndSelectionState() {
        val session = RoomObjectSession(ids)
        session.applySnapshot(SceneSnapshot(setOf(RoomObjectIds.VASE, RoomObjectIds.HEADPHONES)))
        session.place(RoomObjectIds.VASE)
        session.toggleSelection(RoomObjectIds.VASE)

        session.reset()

        assertNull(session.state.selectedObjectId)
        assertEquals(RoomObjectStatus.AVAILABLE, session.state.objectStates[RoomObjectIds.VASE])
        assertEquals(
            RoomObjectStatus.AVAILABLE,
            session.state.objectStates[RoomObjectIds.HEADPHONES],
        )
        assertEquals(
            RoomObjectStatus.UNAVAILABLE,
            session.state.objectStates[RoomObjectIds.DESK_LAMP],
        )
    }
}
