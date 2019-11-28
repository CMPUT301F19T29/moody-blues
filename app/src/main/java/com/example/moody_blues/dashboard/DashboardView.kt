package com.example.moody_blues.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.requests.RequestView

/**
 * Toolkit-specific logic for the dashboard activity
 */
class DashboardView : AppCompatActivity(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter

    private lateinit var history: Button
    private lateinit var feed: Button
    private lateinit var requests: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)
        title = "Hello, " + AppManager.getUsername()

        // Pass the view to the presenter
        presenter = DashboardPresenter(this)

        history = findViewById(R.id.dashboard_history_button)
        feed = findViewById(R.id.dashboard_feed_button)
        requests = findViewById(R.id.dashboard_requests_button)

        // Do stuff with the presenter
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

    /**
     * Transition to the history activity
     */
    override fun gotoHistory() {
        val intent = Intent(this, HistoryView::class.java)
        startActivity(intent)
    }

    /**
     * Transition to the feed activity
     */
    override fun gotoFeed() {
        val intent = Intent(this, FeedView::class.java)
        startActivity(intent)
    }

    /**
     * Transition to the requests activity
     */
    override fun gotoRequests() {
        val intent = Intent(this, RequestView::class.java)
        startActivity(intent)
    }
}

