package com.example.moody_blues.login
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun gotoMain()
    }

    interface Presenter : BasePresenter {
        fun login(user:String, pass:String)
    }
}