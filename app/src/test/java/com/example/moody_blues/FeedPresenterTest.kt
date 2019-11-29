package com.example.moody_blues

import com.example.moody_blues.feed.FeedContract
import com.example.moody_blues.feed.FeedPresenter
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class FeedPresenterTest {
    lateinit var viewSpy : FeedView
    lateinit var mockAppManager : AppManager
    lateinit var presenter : FeedPresenter

    @Before
    fun setupMocks() {
        viewSpy = spyk<FeedView>()
        mockAppManager = spyk<AppManager>()
        every{ mockAppManager["fetchFeed"]() } returns null
        every{ mockAppManager["fetchRequests"]() } returns null
        presenter = FeedPresenter(viewSpy, mockAppManager)
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @Test
    fun testGetFeed() {
        presenter.getFeed()
        verify { mockAppManager.getFeed() }
    }

    @Ignore
    @Test
    fun testGoToMap(){
        presenter.gotoMap()
        verify{ viewSpy.gotoMap() }
    }

}

