package com.example.moody_blues.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryContract
import com.example.moody_blues.history.HistoryPresenter

class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

        // Do stuff with the presenter
    }
}

