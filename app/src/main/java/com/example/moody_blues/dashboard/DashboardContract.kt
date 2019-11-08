package com.example.moody_blues.dashboard
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface DashboardContract {
    interface View : BaseView<Presenter> {
        fun gotoHistory()
        fun gotoFeed()
        fun gotoRequests()
    }

    interface Presenter : BasePresenter {
        fun gotoHistory()
        fun gotoFeed()
        fun gotoRequests()
    }
}
