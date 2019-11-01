package com.example.moody_blues.history
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

interface HistoryContract {
    interface View : BaseView<Presenter> {
        fun gotoMood(mood: Mood)
    }

    interface Presenter : BasePresenter {
        fun createNewMood()
    }
}
