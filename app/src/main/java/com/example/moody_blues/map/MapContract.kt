package com.example.moody_blues.map
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

interface MapContract {
    interface View : BaseView<Presenter> {
        fun gotoMood()
    }

    interface Presenter : BasePresenter {
        fun selectLocation()
    }
}
