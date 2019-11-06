package com.example.moody_blues.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.mood.MoodView

class MapView : AppCompatActivity(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_view)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
        TODO("implement edge swipe for going back\n" +
                "implement map and location tapping")

        // make a button
        // bind button to go back
        // on click listener button
        // finish();

    }

    override fun gotoMood() {
        val intent = Intent(this, MoodView::class.java)
        startActivity(intent)
    }
}

