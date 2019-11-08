package com.example.moody_blues.history
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

interface HistoryContract {
    interface View : BaseView<Presenter> {
        fun gotoMood(mood: Mood)
        fun gotoEditMood(pos: Int)
    }

    interface Presenter : BasePresenter {
        fun fetchMoods(): ArrayList<Mood>
        fun createMood(location: Location?)
        fun addMood(mood: Mood)
        fun editMood(pos: Int)
        fun updateMood(mood: Mood, pos: Int)
    }
}
