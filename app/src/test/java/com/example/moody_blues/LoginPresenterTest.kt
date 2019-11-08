package com.example.moody_blues

import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class LoginPresenterTest {
    private val mockView: LoginContract.View = mock(LoginContract.View::class.java)
    private val presenter = LoginPresenter(mockView)

    @Ignore
    @Test
    fun testLogin() {
        // TODO: Issues with coroutines and tests
        presenter.login("los@ualberta.ca", "password")
        Mockito.verify(mockView).gotoDashboard()
    }

    @Test
    fun testsSignup() {
        presenter.signup()
        Mockito.verify(mockView).gotoSignUp()
    }

}

