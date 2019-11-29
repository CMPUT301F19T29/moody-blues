package com.example.moody_blues

import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.map.MapContract
import com.example.moody_blues.map.MapPresenter
import com.example.moody_blues.map.MapView
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class MapPresenterTest {
    lateinit var viewSpy : MapView
    lateinit var mockAppManager : AppManager
    lateinit var presenter : MapPresenter

    @Before
    fun setupMocks() {
        viewSpy = spyk<MapView>()
        every { viewSpy["getLocation"]() } returns null
        mockAppManager = spyk<AppManager>()
        presenter = MapPresenter(viewSpy)
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @Test
    fun testGetLocation() {
        presenter.getLocation()
        verify { viewSpy.getLocation() }
    }
}

