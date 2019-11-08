package com.example.moody_blues.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.dashboard.DashboardView

class LoginView : AppCompatActivity(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Pass the view to the presenter
        presenter = LoginPresenter(this)

        // Do stuff with the presenter
        val submit: Button = findViewById(R.id.login_submit_button)
        val user: EditText = findViewById(R.id.login_user_field)
        val pass: EditText = findViewById(R.id.login_pass_field)

        submit.setOnClickListener {
            presenter.login(user.text.toString(), pass.text.toString())
        }
    }

    override fun gotoDashboard() {
        val intent = Intent(this, DashboardView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // clear activity stack
        startActivity(intent)
    }


}

