package com.example.moody_blues.requests

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.moody_blues.R
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.login.LoginView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

/**
 * Toolkit-specific logic for the request activity
 */
class RequestView : AppCompatActivity(), RequestContract.View {
    override lateinit var presenter: RequestContract.Presenter
    private lateinit var viewPager: ViewPager
    private lateinit var requestsPageAdapter: RequestsPageAdapter
    private lateinit var addRequestButton: FloatingActionButton
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_view)
        title = "Requests"

        addRequestButton = findViewById(R.id.request_add_button)

        // Setup a toolbar as a replacement for the action bar
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar);

        // Initialize an icon in the toolbar (to be replaced with hamburger icon after)
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

        // Setup drawer view
        setupDrawerContent(nvDrawer)

        requestsPageAdapter =
            RequestsPageAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = requestsPageAdapter
        val tabs: TabLayout = findViewById(R.id.request_tab_bar)
        tabs.setupWithViewPager(viewPager)

        // Pass the view to the presenter
        presenter = RequestPresenter(this)

        // Do stuff with the presenter
        addRequestButton.setOnClickListener {
            val builder: AlertDialog.Builder? = AlertDialog.Builder(this)
            builder
                ?.setView(R.layout.request_dialog)
                ?.setTitle("Follow a user")
                ?.setPositiveButton("Ok",  DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this, "Making a request...", Toast.LENGTH_SHORT).show()
                    val field = (dialog as Dialog).findViewById<EditText>(R.id.request_dialog_field)
                    presenter.requestFollow(field.text.toString())
                })
                ?.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    (dialog as Dialog).cancel()
                })
            builder?.show()
        }

    }

    // Toggle on post create
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        // For setting up the drawer toggle
        return ActionBarDrawerToggle(
            this,
            mDrawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
    }

    private fun setupDrawerContent(navigationView:NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        // Go to the activity when item is clicked
        when (menuItem.itemId) {
            
            R.id.nav_history -> startActivity(Intent(this, HistoryView::class.java))
            R.id.nav_feed -> startActivity(Intent(this, FeedView::class.java))
            R.id.nav_login -> startActivity(Intent(this, LoginView::class.java))
//            else -> fragmentClass = HistoryView::class.java
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
//        // Set action bar title
//        title = menuItem.title

        // Close the navigation drawer
        finish()
        mDrawer.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // Overrides the back button to close the drawer when it is open
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun updateList() {
        for (fragment in supportFragmentManager.fragments) {
            val rf: RequestFragment = fragment as RequestFragment
            rf.update()
        }
    }
}

