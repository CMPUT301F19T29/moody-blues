package com.example.moody_blues

import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.models.Mood
import com.google.firebase.auth.FirebaseAuth
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Test

class HistoryPresenterTest {
    val viewSpy = spyk<HistoryView>()

    @Test
    fun testsSomething() {
        val mockAppManager = spyk<AppManager>()
        val presenter = HistoryPresenter(viewSpy, true, mockAppManager = mockAppManager)
        presenter.fetchMoods(1)
        verify { mockAppManager.getOrderedUserMoods(0) }
    }

}

