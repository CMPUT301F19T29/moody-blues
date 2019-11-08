package com.example.moody_blues

import com.example.moody_blues.map.MapContract
import com.example.moody_blues.map.MapPresenter
import org.junit.Test
import org.mockito.Mockito.mock

class MapPresenterTest {

    @Test
    fun testsLogin() {
        val mockView = mock(MapContract.View::class.java)
        val presenter = MapPresenter(mockView)
    }

}

