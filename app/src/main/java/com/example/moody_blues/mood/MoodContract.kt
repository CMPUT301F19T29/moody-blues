package com.example.moody_blues.mood
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

/**
 * The contract between the mood view and the presenter
 */
interface MoodContract {
    /**
     * The mood view
     */
    interface View : BaseView<Presenter> {
        /**
         * Transition back to the history activity
         */
        fun backtoHistory()
//        fun gotoMap()
    }

    /**
     * The mood prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Confirm adding or editing a mood
         */
        fun confirmMood(mood: Mood)
    }
}
