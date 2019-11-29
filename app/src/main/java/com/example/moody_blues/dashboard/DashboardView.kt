package com.example.moody_blues.dashboard

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.AppManager
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.moody_blues.R
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.login.LoginView
import com.example.moody_blues.requests.RequestView
import com.google.android.material.navigation.NavigationView

/**
 * Toolkit-specific logic for the dashboard activity
 */
class DashboardView : AppCompatActivity(), DashboardContract.View {
    override lateinit var presenter: DashboardContract.Presenter
    private lateinit var history: Button
    private lateinit var feed: Button
    private lateinit var requests: Button
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView
    private lateinit var menu: Menu
    private lateinit var menuText: MenuItem
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_view)
        title = "Hello, " + AppManager.getUsername()

        // Pass the view to the presenter
        presenter = DashboardPresenter(this)

        // Define buttons for history, feed and requests
        history = findViewById(R.id.dashboard_history_button)
        feed = findViewById(R.id.dashboard_feed_button)
        requests = findViewById(R.id.dashboard_requests_button)

        // Setup a toolbar as a replacement for the action bar
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar);

        // Initialize an icon in the toolbar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // Initialize DrawerLayout and define the toggle for the drawer
        mDrawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawerToggle = setupDrawerToggle()

        // Setup toggle to display hamburger icon
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Initialize NavigationView
        nvDrawer = findViewById(R.id.nvView) as NavigationView
        nvDrawer.setItemIconTintList(null)

        // Get menu
        menu = nvDrawer.getMenu()

        // Get menu item for text and set it
        menuText = menu.findItem(R.id.welcome_text)
        menuText.setTitle(title)

        // Setup drawer view
        setupDrawerContent(nvDrawer)

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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        // Syncs the drawer toggle state
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // Pass configuration changes to the drawer toggle
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    /**
     * Setup for the drawer toggle
     */
    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(
            this,
            mDrawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    /**
     * Setup for the drawer content
     * @param navigationView The navigation view for displaying the menu
     */
    private fun setupDrawerContent(navigationView:NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    /**
     * Goes to the activity when the respective item has been clicked in the drawer
     * @param menuItem The menu item from the drawer
     */
    private fun selectDrawerItem(menuItem: MenuItem) {
        // Go to the activity when item is clicked
        when (menuItem.itemId) {
            R.id.nav_history -> startActivity(Intent(this, HistoryView::class.java))
            R.id.nav_feed -> startActivity(Intent(this, FeedView::class.java))
            R.id.nav_requests -> startActivity(Intent(this, RequestView::class.java))
            R.id.nav_login -> startActivity(Intent(this, LoginView::class.java))
            else -> return
        }

        // Highlight the selected item has been done
        menuItem.isChecked = true

        // Close the drawer
        mDrawer.closeDrawers()
    }

    /**
     * Hamburger icon will open the drawer
     * @param item The hamburger icon
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Overrides back button to close drawer if it is open
     */
    override fun onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers()
        } else {
            super.onBackPressed()
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

