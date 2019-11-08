package com.example.moody_blues.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.requests.RequestView

class DashboardView : AppCompatActivity(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)

        // Pass the view to the presenter
        presenter = DashboardPresenter(this)

        // Do stuff with the presenter
        val history: Button = findViewById(R.id.dashboard_history_button)
        val feed: Button = findViewById(R.id.dashboard_feed_button)
        val requests: Button = findViewById(R.id.dashboard_requests_button)

        history.setOnClickListener {
            presenter.gotoHistory()
        }

        feed.setOnClickListener {
            presenter.gotoFeed()
        }

        requests.setOnClickListener {
            presenter.gotoRequests()
        }
    }

    override fun gotoHistory() {
        val intent = Intent(this, HistoryView::class.java)
        startActivity(intent)
    }

    override fun gotoFeed() {
        val intent = Intent(this, FeedView::class.java)
        startActivity(intent)
    }

    override fun gotoRequests() {
        val intent = Intent(this, RequestView::class.java)
        startActivity(intent)
    }
}

