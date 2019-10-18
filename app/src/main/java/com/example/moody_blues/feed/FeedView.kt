package com.example.moody_blues.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R

class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)

        // Pass the view to the presenter
        presenter = FeedPresenter(this)

        // Do stuff with the presenter
    }
}

