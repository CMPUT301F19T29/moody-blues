package com.example.moody_blues.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.dashboard.DashboardView
import com.example.moody_blues.signup.SignupView

/**
 * Toolkit-specific logic for the login activity
 */
class LoginView : AppCompatActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter

    private lateinit var submit: Button
    private lateinit var signup: Button
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var logo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Pass the view to the presenter
        presenter = LoginPresenter(this)

        submit = findViewById(R.id.login_submit_button)
        signup = findViewById(R.id.login_signup_button)
        user = findViewById(R.id.login_user_field)
        pass = findViewById(R.id.login_pass_field)
        logo = findViewById(R.id.login_logo)

        // Do stuff with the presenter
        submit.setOnClickListener {
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
            presenter.login(user.text.toString(), pass.text.toString())
        }

        signup.setOnClickListener {
            presenter.signup()
        }
    }

    /**
     * Clear the fields for username and password
     */
    override fun onError(){
        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
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

