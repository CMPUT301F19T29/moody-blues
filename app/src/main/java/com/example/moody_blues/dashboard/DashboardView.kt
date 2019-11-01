package com.example.moody_blues.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView

class DashboardView : AppCompatActivity(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)

        // Pass the view to the presenter
        presenter = DashboardPresenter(this)

        // Do stuff with the presenter
        val history: Button = findViewById(R.id.dashboard_history_button)

        history.setOnClickListener {
            presenter.gotoHistory()
        }
    }

    override fun gotoHistory() {
        val intent = Intent(this, HistoryView::class.java)
        startActivity(intent)
    }
}

