package com.example.moody_blues.map

import com.example.moody_blues.AppManager
import com.example.moody_blues.signup.SignupContract
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

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

    override fun confirmSignup(email: String, password: String, username: String) {
        MainScope().launch {
            try{
                if (AppManager.createUser(email, password, username) == null){
                    signupView.clear()
                }
                else{
                    signupView.backtoLogin()
                }
            }
            catch (ex: Exception){
                signupView.clear()
            }
        }
    }
}
