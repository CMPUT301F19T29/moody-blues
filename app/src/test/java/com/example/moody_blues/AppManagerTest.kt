package com.example.moody_blues

import com.example.moody_blues.models.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import junit.framework.Assert.assertEquals


class AppManagerTest {
    // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
    val mainThreadSurrogate = newSingleThreadContext("Presenter Thread")
    lateinit var appManager : AppManager
    lateinit var mockDB: DbManager

    @Before
    fun setUpCoTest() {
        Dispatchers.setMain(mainThreadSurrogate)
        appManager = AppManager
        mockDB = mockkClass(DbManager::class)
        appManager.init(mockDB)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Before
    fun setUp() = MockKAnnotations.init()

    @Test
    fun testsSignup() = runBlockingTest {
        coEvery { mockDB.signIn("jordan", "test") } returns "jordan"
        coEvery { mockDB.getUser("jordan") } returns User(username = "Jordan")
        val username = appManager.signIn("jordan","test")

        assertEquals("jordan", username)
    }

}

