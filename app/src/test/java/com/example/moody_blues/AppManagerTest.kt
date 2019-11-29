package com.example.moody_blues

import android.content.Context
import com.example.moody_blues.map.SignupPresenter
import com.example.moody_blues.signup.SignupContract
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.mockk.Runs
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock

class AppManagerTest {
    // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test
    val mainThreadSurrogate = newSingleThreadContext("Presenter Thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun testsSignup() = runBlockingTest {
        val appManager = AppManager
        val mockDB = spyk<DbManager>()
        appManager.init(mockDB)
        appManager.signIn("jordan","test")
    }

}

