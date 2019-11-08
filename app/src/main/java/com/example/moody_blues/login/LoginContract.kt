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
        fun gotoDashboard()
        fun gotoSignUp()
        fun clear()
    }

    /**
     * The login prsenter
     */
    interface Presenter : BasePresenter {
        fun login(user:String, pass:String)
        fun signup()
    }
}
