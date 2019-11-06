package com.example.moody_blues.requests
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface RequestContract {
    interface View : BaseView<Presenter> {
        fun gotoFollowUser()
    }

    interface Presenter : BasePresenter {
        fun requestFollow(user: String)
    }
}
