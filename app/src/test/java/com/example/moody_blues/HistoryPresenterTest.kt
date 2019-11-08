package com.example.moody_blues

import com.example.moody_blues.history.HistoryContract
import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class HistoryPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(HistoryContract.View::class.java)
        val presenter = HistoryPresenter(mockView)

//        presenter.login("User", "pass")
//        Mockito.verify(mockView).gotoDashboard()
    }

}

