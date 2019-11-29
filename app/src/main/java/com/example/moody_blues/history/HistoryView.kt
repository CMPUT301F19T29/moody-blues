package com.example.moody_blues.history

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.dashboard.DashboardView
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.login.LoginView
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.example.moody_blues.mood.MoodView
import com.example.moody_blues.mood.MoodView.Companion.INTENT_MOOD_RESULT
import com.example.moody_blues.mood.MoodView.Companion.INTENT_POS_RESULT
import com.example.moody_blues.requests.RequestView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.history_view.*
import kotlinx.coroutines.GlobalScope

/**
 * Toolkit-specific logic for the history activity
 */
class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var filterField: Spinner
    private lateinit var add: FloatingActionButton
    private lateinit var mapButton: FloatingActionButton
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView
    private lateinit var menu: Menu
    private lateinit var menuText: MenuItem

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        title = "History"

        add =  findViewById(R.id.history_add_button)
        mapButton = findViewById(R.id.goto_map_button)
        filterField = findViewById(R.id.filter_moods)
        filterField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.EMOTION_FILTERS)
        filterField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter.refreshMoods(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

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

        // get menu
        menu = nvDrawer.getMenu()

        // get menu item for text and set it
        menuText = menu.findItem(R.id.welcome_text)
        menuText.setTitle("Hello, " + AppManager.getUsername())

        // Setup drawer view
        setupDrawerContent(nvDrawer)

        // Do stuff with the presenter
        add.setOnClickListener {
            presenter.onAddMood()
        }

        mapButton.setOnClickListener {
            presenter.gotoMap()
        }

        history_list_mood.adapter = MoodAdapter(presenter.fetchMoods(0),
            { item: Mood, _: Int -> presenter.onEditMood(item) },
            { item: Mood, _: Int -> presenter.deleteMood(item)
                history_list_mood.adapter!!.notifyDataSetChanged()
                true })
        history_list_mood.layoutManager = LinearLayoutManager(this)
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

            R.id.nav_dashboard -> startActivity(Intent(this, DashboardView::class.java))
            R.id.nav_feed -> startActivity(Intent(this, FeedView::class.java))
            R.id.nav_requests -> startActivity(Intent(this, RequestView::class.java))
            R.id.nav_login -> startActivity(Intent(this, LoginView::class.java))
            else -> return
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

    // Overrides the back button to close the drawer when it is open
    override fun onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Fetch the user's current location
     */
    override fun getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        else {
            getLocationResult()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0)
            getLocationResult()
    }

    /**
     * Create a new mood when location is obtained
     */
    private fun getLocationResult() {
        fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location : Location? ->
                    presenter.createMood(location)
                }
    }

    /**
     * Callback function after a user returns from the mood activity
     * @param requestCode A code for creating a new mood or editing an existing one
     * @param resultCode A code for success
     * @param data The intent passed by the mood activity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.refreshMoods(filterField.selectedItemPosition)
    }

    /**
     * Open the mood activity for a mood
     * @param mood The mood the edit or view
     */
    override fun gotoMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_PURPOSE, INTENT_PURPOSE_ADD)
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_MOOD_CODE)
    }

    /**
     * Update the list of displayed moods
     * @param moods The new list of moods
     */
    override fun refreshMoods(moods: ArrayList<Mood>) {
        val moodAdapter = history_list_mood.adapter as MoodAdapter
        moodAdapter.refresh(moods)
    }

    /**
     * Open the mood activity for a mood
     * @param mood The mood to edit or view
     */
    override fun gotoEditMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_PURPOSE, INTENT_PURPOSE_EDIT)
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_EDITED_MOOD_CODE)
    }

    /**
     * Transition to the map activity
     */
    override fun gotoMap() {
        val intent = Intent(this, MapView::class.java)
        intent.putExtra("mode", 1)
        startActivity(intent)
    }

    companion object {
        const val INTENT_PURPOSE = "purpose"
        const val INTENT_PURPOSE_EDIT = "Edit Mood"
        const val INTENT_PURPOSE_ADD = "New Mood"
        const val INTENT_MOOD = "mood"
        const val INTENT_EDIT_ID = "edit id"
        const val GET_MOOD_CODE = 1
        const val GET_EDITED_MOOD_CODE = 2
    }
}