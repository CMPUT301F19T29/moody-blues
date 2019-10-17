package com.example.moody_blues.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R

class MapView : AppCompatActivity(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_view)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
    }
}

