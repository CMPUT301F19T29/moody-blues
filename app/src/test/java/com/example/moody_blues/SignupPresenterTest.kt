package com.example.moody_blues

import com.example.moody_blues.map.SignupPresenter
import com.example.moody_blues.signup.SignupContract
import org.junit.Test
import org.mockito.Mockito.mock

class SignupPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(SignupContract.View::class.java)
        val presenter = SignupPresenter(mockView)
    }

}

