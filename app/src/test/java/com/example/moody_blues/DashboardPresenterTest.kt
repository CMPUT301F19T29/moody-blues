package com.example.moody_blues

import com.example.moody_blues.dashboard.DashboardContract
import com.example.moody_blues.dashboard.DashboardPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DashboardPresenterTest {
    val mockView: DashboardContract.View = mock(DashboardContract.View::class.java)
    val presenter = DashboardPresenter(mockView)

    @Test
    fun testsHistory() {
        presenter.gotoHistory()
        Mockito.verify(mockView).gotoHistory()
    }

    @Test
    fun testsFeed() {
        presenter.gotoFeed()
        Mockito.verify(mockView).gotoFeed()
    }

    @Test
    fun testsRequests() {
        presenter.gotoRequests()
        Mockito.verify(mockView).gotoRequests()
    }
}

