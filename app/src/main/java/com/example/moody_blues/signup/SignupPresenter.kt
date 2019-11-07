package com.example.moody_blues.map

import com.example.moody_blues.signup.SignupContract

class SignupPresenter(val signupView: SignupContract.View) : SignupContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        signupView.presenter = this
    }

    override fun start() {
    }
}
