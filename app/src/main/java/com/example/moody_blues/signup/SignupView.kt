package com.example.moody_blues.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.map.SignupPresenter
import com.example.moody_blues.signup.SignupContract
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupView : AppCompatActivity(), SignupContract.View {
    override lateinit var presenter: SignupContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_view)

        // Pass the view to the presenter
        presenter = SignupPresenter(this)

        var email = findViewById<EditText>(R.id.signup_email_field)
        var pass = findViewById<EditText>(R.id.signup_password_field)
        var username = findViewById<EditText>(R.id.signup_username_field)

        // Do stuff with the presenter
        val confirm: Button = findViewById(R.id.signup_confirm_button)

        confirm.setOnClickListener {
            presenter.confirmSignup(email.text.toString(), pass.text.toString(), username.text.toString())
        }
    }

    override fun clear(){
        var email = findViewById<EditText>(R.id.signup_email_field)
        var pass = findViewById<EditText>(R.id.signup_password_field)
        var username = findViewById<EditText>(R.id.signup_username_field)

        email.text.clear()
        pass.text.clear()
        username.text.clear()
    }

    override fun backtoLogin() {
        finish()
    }
}

