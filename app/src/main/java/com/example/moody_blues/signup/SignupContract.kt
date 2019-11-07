package com.example.moody_blues.signup
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface SignupContract {
    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter {
    }
}
