package com.example.moody_blues.login
import android.content.Intent
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun gotoDashboard()
    }

    interface Presenter : BasePresenter {
        fun login(user:String, pass:String)
    }
}
