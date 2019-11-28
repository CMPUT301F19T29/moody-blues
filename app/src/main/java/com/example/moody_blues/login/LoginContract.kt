package com.example.moody_blues.login
import android.content.Intent
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * Declares public functions of the login view and the presenter
 */
interface LoginContract {
    /**
     * The login view
     */
    interface View : BaseView<Presenter> {
        /**
         * Transition to the dashboard activity
         */
        fun gotoDashboard()
        /**
         * Transition to the sign up activity
         */
        fun gotoSignUp()
        /**
         * Alerts the user of errors
         */
        fun onError()
    }

    /**
     * The login presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Attempt to log the user in
         * @param user The attempted username / email
         * @param pass The attempted password
         */
        fun login(user:String, pass:String)
        /**
         * Tell the view to go to the sign up activity
         */
        fun signup()
    }
}
