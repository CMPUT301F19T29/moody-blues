package com.example.moody_blues.login

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * The presenter for the login activity
 */
class LoginPresenter(val loginView: LoginContract.View) : LoginContract.Presenter {
    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        loginView.presenter = this
        val user: User
9
        this.start()
    }

    override fun start() {
    }

    /**
     * Attempt to log the user in
     * @param user The attempted username / email
     * @param pass The attempted password
     */
    override fun login(user: String, pass: String) {
        MainScope().launch {

            try{
                if (AppManager.signIn(user, pass) == null){
                    loginView.clear()
                }
                else{
                    loginView.gotoDashboard()
                }
            }
            catch (ex: Exception){
                loginView.clear()
            }
            // TODO: Show an error of some kind to the user
//                loginView.clear()
        }
    }

    /**
     * Tell the view to go to the sign up activity
     */
    override fun signup() {
        loginView.gotoSignUp()
    }
}
