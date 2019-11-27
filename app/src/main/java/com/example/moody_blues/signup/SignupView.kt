package com.example.moody_blues.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.map.SignupPresenter
import com.example.moody_blues.signup.SignupContract
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Toolkit-specific logic for the sign up activity
 */
class SignupView : AppCompatActivity(), SignupContract.View {
    override lateinit var presenter: SignupContract.Presenter

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var username: EditText
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_view)
        title = "Signup"

        // Pass the view to the presenter
        presenter = SignupPresenter(this)

        email = findViewById(R.id.signup_email_field)
        pass = findViewById(R.id.signup_password_field)
        username = findViewById(R.id.signup_username_field)
        confirm = findViewById(R.id.signup_confirm_button)

        // Do stuff with the presenter
        confirm.setOnClickListener {
            presenter.confirmSignup(email.text.toString(), pass.text.toString(), username.text.toString())
        }
    }

    /**
     * Clear the fields for email, password, and username
     */
    override fun clear(){
        email.text.clear()
        pass.text.clear()
        username.text.clear()
    }

    /**
     * Return to the login activity
     */
    override fun backtoLogin() {
        finish()
    }

    override fun showError(error: String){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}

