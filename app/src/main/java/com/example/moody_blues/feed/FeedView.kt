package com.example.moody_blues.feed

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.login.LoginView
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.example.moody_blues.requests.RequestView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Toolkit-specific logic for the feed activity
 */
class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter

    private lateinit var mapButton: FloatingActionButton
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)
        title = "Feed"

        // Pass the view to the presenter
        presenter = FeedPresenter(this)

        mapButton = findViewById(R.id.feed_goto_map_button)

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

        // Do stuff with the presenter
        feed_list_mood.adapter = MoodAdapter(presenter.getFeed(),
            {mood: Mood, _: Int ->
                if (mood.reasonImageFull != null) {
                    var imageView = ImageView(this)
                    
                    var builder = Dialog(this)
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    builder.window?.setBackgroundDrawable(
                            ColorDrawable(Color.TRANSPARENT))
                    builder.setOnDismissListener{
                        Picasso.get().cancelRequest(imageView)
                        imageView.setImageResource(android.R.color.transparent)
                    }

                    imageView.setOnClickListener{
                        builder.dismiss()
                    }
                    builder.addContentView(imageView, RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT))
                    builder.show()

                    MainScope().launch {
                        var (uri, rotation) = AppManager.getImageUri(mood.username, mood.reasonImageFull!!)
                        if (uri != null) {
                            Picasso.get().load(uri).rotate(rotation).into(imageView)
                        } else {
                            imageView.setImageResource(android.R.color.transparent)
                        }
                    }
                }
            },
            { _: Mood, _: Int -> true })

        feed_list_mood.layoutManager = LinearLayoutManager(this)

        mapButton.setOnClickListener {
            presenter.gotoMap()
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
            R.id.nav_requests -> startActivity(Intent(this, RequestView::class.java))
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

    /**
     * Refresh the feed
     * @param feed The new feed to display
     */
    override fun refreshFeed(feed: ArrayList<Mood>) {
        val feedAdapter = feed_list_mood.adapter as MoodAdapter
        feedAdapter.refresh(feed)
    }

    /**
     * Transition to the map activity
     */
    override fun gotoMap() {
        val intent = Intent(this, MapView::class.java)
        intent.putExtra("mode", 2)
        startActivity(intent)
    }
}

