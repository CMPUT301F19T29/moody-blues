package com.example.moody_blues.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R

class LoginView : AppCompatActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Pass the view to the presenter
        presenter = LoginPresenter(this)

        // Do stuff with the presenter
    }
}

