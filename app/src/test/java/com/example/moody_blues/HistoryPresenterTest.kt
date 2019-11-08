package com.example.moody_blues

import com.example.moody_blues.history.HistoryContract
import com.example.moody_blues.history.HistoryPresenter
import org.junit.Test
import org.mockito.Mockito.mock

class HistoryPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(HistoryContract.View::class.java)
        val presenter = HistoryPresenter(mockView)
    }

}

