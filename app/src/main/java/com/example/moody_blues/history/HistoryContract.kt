package com.example.moody_blues.history
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

/**
 * The contract between the history view and the presenter
 */
interface HistoryContract {
    /**
     * The history view
     */
    interface View : BaseView<Presenter> {
        fun gotoMood(mood: Mood)
        fun gotoEditMood(id: String)
        fun refreshMoods(moods: ArrayList<Mood>)
    }

    /**
     * The history prsenter
     */
    interface Presenter : BasePresenter {
        fun fetchMoods(emotion: String): ArrayList<Mood>
        fun fetchMoods(): ArrayList<Mood>
        fun createMood(location: Location?)
        fun addMood(mood: Mood)
        fun editMood(mood: Mood)
        fun updateMood(mood: Mood)
        fun deleteMood(mood: Mood)
        fun refreshMoods(emotion: String)
        fun refreshMoods()
    }
}
