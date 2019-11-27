package com.example.moody_blues

import com.example.moody_blues.dashboard.DashboardPresenter
import com.example.moody_blues.dashboard.DashboardView
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class DashboardPresenterTest {
    val viewSpy = spyk<DashboardView>()
    val presenter = DashboardPresenter(viewSpy)


    @Test
    fun testsHistory() {
        presenter.gotoHistory()
        verify { viewSpy.gotoHistory() }
    }

    @Test
    fun testsFeed() {
        val presenter = DashboardPresenter(viewSpy)
        presenter.gotoFeed()
        verify { viewSpy.gotoFeed() }
    }

    @Test
    fun testsRequests() {
        val presenter = DashboardPresenter(viewSpy)
        presenter.gotoRequests()
        verify { viewSpy.gotoRequests() }
    }
}

