package com.example.moody_blues

import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class LoginPresenterTest {

    @Test
    fun testsSignup() {
        val mockView = mock(LoginContract.View::class.java)
        val presenter = LoginPresenter(mockView)
        presenter.signup()
        Mockito.verify(mockView).gotoSignUp()
    }

}

