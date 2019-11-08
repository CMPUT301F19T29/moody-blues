package com.example.moody_blues

import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class FeedPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(LoginContract.View::class.java)
        val presenter = LoginPresenter(mockView)

        presenter.login("User", "pass")
        Mockito.verify(mockView).gotoDashboard()
    }

}

