package com.example.moody_blues.requests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.moody_blues.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class RequestView : AppCompatActivity(), RequestContract.View {
    override lateinit var presenter: RequestContract.Presenter
    private lateinit var request: FloatingActionButton
    private lateinit var viewPager: ViewPager
    private lateinit var requestsPageAdapter: RequestsPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_view)
        title = "requests"

        requestsPageAdapter =
            RequestsPageAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = requestsPageAdapter
        val tabs: TabLayout = findViewById(R.id.request_tab_bar)
        tabs.setupWithViewPager(viewPager)

        // Pass the view to the presenter
        presenter = RequestPresenter(this)

        // Do stuff with the presenter
        request = findViewById(R.id.request_add_button)

        request.setOnClickListener {
            gotoFollowUser()
            Snackbar.make(it, "Popup opened!", Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * Open a popup to select a user to follow
     */
    override fun gotoFollowUser() {
        // opens a popup to enter a user to follow

    }
}

