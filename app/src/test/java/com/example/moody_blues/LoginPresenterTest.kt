package com.example.moody_blues

import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import com.example.moody_blues.login.LoginView
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class LoginPresenterTest {
    lateinit var viewSpy : LoginView
    lateinit var mockAppManager : AppManager
    lateinit var presenter : LoginPresenter

    @Before
    fun setupMocks() {
        viewSpy = spyk<LoginView>()
        mockAppManager = spyk<AppManager>()
        presenter = LoginPresenter(viewSpy)
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @Ignore
    @Test
    fun testFetchMoods() {
        presenter.login("username", "password")
        verify { mockAppManager.getOrderedUserMoods(0) }
    }
}

