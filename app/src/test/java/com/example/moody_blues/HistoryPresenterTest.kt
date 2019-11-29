package com.example.moody_blues

import android.location.Location
import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.models.Mood
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentCaptor


class HistoryPresenterTest {
    lateinit var viewSpy : HistoryView
    lateinit var mockAppManager : AppManager
    lateinit var presenter : HistoryPresenter

    @Before
    fun setupMocks() {
        viewSpy = spyk<HistoryView>()
        mockAppManager = spyk<AppManager>()
        presenter = HistoryPresenter(viewSpy, mockAppManager)
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @Test
    fun testFetchMoods() {
        presenter.fetchMoods(1)
        verify { mockAppManager.getOrderedUserMoods(0) }
    }

    @Test
    fun testAddMood() {
        presenter.onAddMood()
        verify { viewSpy.getLocation() }
    }

    @Ignore
    @Test
    fun testCreateMood() {
        val argument: ArgumentCaptor<Mood> = ArgumentCaptor.forClass<Mood>(Mood::class.java)
        val location = Location("Edmonton")
        presenter.createMood(location)
        verify { viewSpy.gotoMood(argument.capture()) }
        assertEquals(location, argument.value.location)
    }

    @Test
    fun testsGoToMap() {
        presenter.gotoMap()
        verify { viewSpy.gotoMap() }
    }
}

