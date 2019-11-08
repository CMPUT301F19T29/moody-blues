package com.example.moody_blues.mood
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

interface MoodContract {
    interface View : BaseView<Presenter> {
        fun backtoHistory()
//        fun gotoMap()
    }

    interface Presenter : BasePresenter {
        fun confirmMood(mood: Mood)
    }
}
