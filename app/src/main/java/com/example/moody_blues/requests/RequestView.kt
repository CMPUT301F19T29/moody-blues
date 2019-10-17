package com.example.moody_blues.requests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.map.MapContract
import com.example.moody_blues.map.MapPresenter

class RequestView : AppCompatActivity(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_view)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
    }
}

