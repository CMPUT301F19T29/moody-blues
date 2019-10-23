package com.example.moody_blues.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        presenter.start()

        val button1:Button = findViewById(R.id.button1)
        val field1:EditText = findViewById(R.id.field1)
        val field2:EditText = findViewById(R.id.field2)

        button1.setOnClickListener {
            presenter!!.login(field1.text.toString(), field2.text.toString())
        }

    }


}

