package com.example.moody_blues.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.map.MapContract
import com.example.moody_blues.map.MapPresenter

class DashboardView : AppCompatActivity(), MapContract.View {
    override lateinit var presenter: MapContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
    }
}

