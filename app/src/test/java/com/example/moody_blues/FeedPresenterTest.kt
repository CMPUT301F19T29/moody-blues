package com.example.moody_blues

import com.example.moody_blues.feed.FeedContract
import com.example.moody_blues.feed.FeedPresenter
import com.example.moody_blues.login.LoginContract
import com.example.moody_blues.login.LoginPresenter
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class FeedPresenterTest {

    @Ignore
    @Test
    fun testsSomething() {
        // TODO: write something to test
        val mockView = mock(FeedContract.View::class.java)
        val presenter = FeedPresenter(mockView)
    }

}

