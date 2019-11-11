package com.example.moody_blues.signup
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the signup view and the presenter
 */
interface SignupContract {
    /**
     * The signup view
     */
    interface View : BaseView<Presenter> {
        /**
         * Return to the login activity
         */
        fun backtoLogin()
        /**
         * Clear the fields for email, password, and username
         */
        fun clear()
    }

    /**
     * The signup prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Attempt to create a new user
         * @param email The email for the new user
         * @param password The password for the new user
         * @param username The username for the new user
         */
        fun confirmSignup(email: String, password: String, username: String)
    }
}
