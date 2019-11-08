package com.example.moody_blues.signup
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface SignupContract {
    interface View : BaseView<Presenter> {
        fun backtoLogin()
        fun clear()
    }

    interface Presenter : BasePresenter {
        fun confirmSignup(email: String, password: String, username: String)
    }
}
