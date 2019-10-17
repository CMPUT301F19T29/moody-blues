package com.example.moody_blues.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.map.MapContract
import com.example.moody_blues.map.MapPresenter

class FeedView : AppCompatActivity(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
    }
}

