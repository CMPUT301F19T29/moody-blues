package com.example.moody_blues

import android.location.Location
import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.models.Mood
import io.mockk.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

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

    @Ignore
    @Test
    fun testAddMood() {
        presenter.onAddMood()
        verify { viewSpy.getLocation() }
    }

    @Ignore
    @Test
    fun testCreateMood() {
        presenter.createMood(Location("Edmonton"))
        verify { viewSpy.gotoMood(Mood(Location("Edmonton"))) }
    }

    @Test
    fun testsGoToMap() {
        presenter.gotoMap()
        verify { viewSpy.gotoMap() }
    }
}

