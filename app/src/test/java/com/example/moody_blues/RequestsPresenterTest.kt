package com.example.moody_blues

import com.example.moody_blues.requests.RequestContract
import com.example.moody_blues.requests.RequestPresenter
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock

class RequestsPresenterTest {

    @Ignore
    @Test
    fun testsLogin() {
        val mockView = mock(RequestContract.View::class.java)
        val presenter = RequestPresenter(mockView)
    }

}

