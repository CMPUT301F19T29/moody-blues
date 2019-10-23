package com.example.moody_blues.dashboard
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface DashboardContract {
    interface View : BaseView<Presenter> {}

    interface Presenter : BasePresenter {}
}