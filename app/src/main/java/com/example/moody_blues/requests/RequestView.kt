package com.example.moody_blues.requests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R

class RequestView : AppCompatActivity(), RequestContract.View {
    override lateinit var presenter: RequestContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_view)

        // Pass the view to the presenter
        presenter = RequestPresenter(this)

        // Do stuff with the presenter

    }

    override fun gotoFollowUser() {
        // opens a popup to enter a user to follow
    }
}

