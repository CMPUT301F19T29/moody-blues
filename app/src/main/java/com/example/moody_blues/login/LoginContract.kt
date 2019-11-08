package com.example.moody_blues.login
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the login view and the presenter
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
         * Clear the fields for username and password
         */
        fun clear()
    }

    /**
     * The login prsenter
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
