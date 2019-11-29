package com.example.moody_blues

import android.graphics.Bitmap
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodContract
import com.example.moody_blues.mood.MoodPresenter
import com.example.moody_blues.mood.MoodView
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.File

class MoodPresenterTest {
    val mainThreadSurrogate = newSingleThreadContext("Presenter Thread")
    lateinit var viewSpy : MoodView
    lateinit var appManager : AppManager
    lateinit var mockDB: DbManager
    lateinit var presenter : MoodPresenter

    @Before
    fun setupMocks() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewSpy = spyk<MoodView>()
        appManager = AppManager
        mockDB = mockkClass(DbManager::class)
        appManager.init(mockDB)
        presenter = MoodPresenter(viewSpy)
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Ignore
    @Test
    fun testOnSelectEmotion() {
        presenter.onSelectEmotion(1)
        verify{ viewSpy.changeBgColor(Mood.EMOTION_COLORS[1])}
    }

    @Ignore
    @Test
    fun testSetMoodFields() {
        val mood = Mood()
        presenter.setMoodFields(mood, 1, 1, "abc", true)
        assertEquals(mood.emotion, 1)
        assertEquals(mood.social, 1)
        assertEquals(mood.reasonText, "abc")
        assertEquals(mood.showLocation, true)
    }

    @Ignore
    @Test
    fun testVerifyMoodFields() {
        presenter.verifyMoodFields("a")
        verify{ viewSpy.preBacktoHistory() }
    }

    @Ignore
    @Test
    fun testAddMood() {
        val mood = Mood()
        presenter.addMood(mood)
        verify{ viewSpy.backtoHistory()}
    }

    @Ignore
    @Test
    fun testEditMood() {
        val mood = Mood()
        coEvery { appManager.editMood(mood) }
        presenter.editMood(mood)
        verify{ viewSpy.backtoHistory()}
    }

    @Ignore
    @Test
    fun testSetPhoto() {
        val bitmap = spyk<Bitmap>()
        val file = spyk<File>()
        presenter.setPhoto(bitmap, file)
        verify{ viewSpy.backtoHistory()}
    }

}

