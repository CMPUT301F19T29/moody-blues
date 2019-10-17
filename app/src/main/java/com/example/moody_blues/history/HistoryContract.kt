package com.example.moody_blues.history
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface HistoryContract {
    interface View : BaseView<Presenter> {}

    interface Presenter : BasePresenter {}
}
