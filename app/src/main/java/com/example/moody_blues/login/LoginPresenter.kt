package com.example.moody_blues.login

import android.content.Context
import android.content.Intent
import com.example.moody_blues.dashboard.DashboardView

class LoginPresenter(val loginView: LoginContract.View) : LoginContract.Presenter {
    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        loginView.presenter = this
        this.start()
    }

    override fun start() {
    }

    override fun login(user: String, pass: String) {
        // validate with firestore, then:
        loginView.gotoDashboard(Intent(loginView as Context, DashboardView::class.java))
    }
}
