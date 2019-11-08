package com.example.moody_blues.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.map.SignupPresenter
import com.example.moody_blues.signup.SignupContract

class SignupView : AppCompatActivity(), SignupContract.View {
    override lateinit var presenter: SignupContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_view)

        // Pass the view to the presenter
        presenter = SignupPresenter(this)

        // Do stuff with the presenter
        val confirm: Button = findViewById(R.id.signup_confirm_button)

        confirm.setOnClickListener {
            presenter.confirmSignup()
        }
    }

    override fun backtoLogin() {
        // send username and password to database
        // other databasey stuff
        finish()
    }
}

