package com.example.moody_blues

import com.example.moody_blues.mood.MoodContract
import com.example.moody_blues.mood.MoodPresenter
import org.junit.Test
import org.mockito.Mockito.mock

class MoodPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(MoodContract.View::class.java)
        val presenter = MoodPresenter(mockView)
    }

}

