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
        fun backtoLogin()
        fun clear()
    }

    /**
     * The signup prsenter
     */
    interface Presenter : BasePresenter {
        fun confirmSignup(email: String, password: String, username: String)
    }
}
