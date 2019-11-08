package com.example.moody_blues

import com.example.moody_blues.dashboard.DashboardContract
import com.example.moody_blues.dashboard.DashboardPresenter
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DashboardPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(DashboardContract.View::class.java)
        val presenter = DashboardPresenter(mockView)

        presenter.gotoHistory()
        Mockito.verify(mockView).gotoHistory()
    }

}

