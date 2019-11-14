package com.example.moody_blues.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.moody_blues.R
import com.example.moody_blues.dashboard.DashboardView
import com.example.moody_blues.feed.FeedView
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.requests.RequestView
import com.example.moody_blues.signup.SignupView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.login_view.view.*

class LoginView : AppCompatActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter

    private lateinit var submit: Button
    private lateinit var signup: Button
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Pass the view to the presenter
        presenter = LoginPresenter(this)

        submit = findViewById(R.id.login_submit_button)
        signup = findViewById(R.id.login_signup_button)
        user = findViewById(R.id.login_user_field)
        pass = findViewById(R.id.login_pass_field)

        // Set a Toolbar to replace the ActionBar
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar);

        // Display an Up icon (<-)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // Find drawer view
        mDrawer = findViewById(R.id.drawer_layout) as DrawerLayout

        // Initialize nvDrawer
        nvDrawer = findViewById(R.id.nvView) as NavigationView

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Do stuff with the presenter
        submit.setOnClickListener {
            presenter.login(user.text.toString(), pass.text.toString())
        }

        signup.setOnClickListener {
            presenter.signup()
        }
    }

    private fun setupDrawerContent(navigationView:NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment? = null
        val fragmentClass: Class<*>
        when (menuItem.itemId) {
            R.id.nav_first_fragment -> fragmentClass = HistoryView::class.java
            R.id.nav_second_fragment -> fragmentClass = FeedView::class.java
            R.id.nav_third_fragment -> fragmentClass = RequestView::class.java
            else -> fragmentClass = HistoryView::class.java
        }

        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Insert the fragment by replacing any existing fragment
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commit()

        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        mDrawer.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.getItemId()) {
            android.R.id.home -> {
                mDrawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Clear the fields for username and password
     */
    override fun clear(){
        user.text.clear()
        pass.text.clear()
    }

    /**
     * Transition to the dashboard activity
     */
    override fun gotoDashboard() {
        val intent = Intent(this, DashboardView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // clear activity stack
        startActivity(intent)
    }

    /**
     * Transition to the sign up activity
     */
    override fun gotoSignUp() {
        val intent = Intent(this, SignupView::class.java)
        startActivity(intent)
    }
}

