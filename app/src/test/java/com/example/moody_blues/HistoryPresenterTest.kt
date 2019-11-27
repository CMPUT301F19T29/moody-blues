package com.example.moody_blues

import com.example.moody_blues.history.HistoryPresenter
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.models.Mood
import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import org.junit.Test

class HistoryPresenterTest {
    val viewSpy = spyk<HistoryView>()
    val presenter = HistoryPresenter(viewSpy)
    val appManager = mockk<AppManager>()



    @Test
    fun testsSomething() {

        val fb = mockk<FirebaseAuth>()
        every { FirebaseAuth.getInstance() } returns mockk(relaxed = true)
        val newMoods = ArrayList<Mood>()
        newMoods.add(Mood())
        newMoods.add(Mood())
        every { appManager.getOrderedUserMoods(0) } returns newMoods

        assertEquals(newMoods, presenter.fetchMoods(0))
    }

}

