package com.example.moody_blues.mood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R

class MoodView : AppCompatActivity(), MoodContract.View {
    override lateinit var presenter: MoodContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        // Do stuff with the presenter
    }
}

