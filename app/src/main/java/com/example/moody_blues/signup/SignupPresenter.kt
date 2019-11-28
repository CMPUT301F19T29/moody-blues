package com.example.moody_blues.map

import android.widget.Toast
import com.example.moody_blues.AppManager
import com.example.moody_blues.signup.SignupContract
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * TThe non-toolkit logic for the sign up activity
 */
class SignupPresenter(private val view: SignupContract.View) : SignupContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Attempt to create a new user
     * @param email The email for the new user
     * @param password The password for the new user
     * @param username The username for the new user
     */
    override fun confirmSignup(email: String, password: String, username: String) {
        // TODO: show waiting animation

        MainScope().launch {
            var error = AppManager.createUser(email, password, username)
            if (error == null)
                view.backtoLogin()
            else
                view.showError(error)
        }
    }
}
