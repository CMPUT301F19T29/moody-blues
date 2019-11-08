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
        /**
         * Open the mood activity for a mood
         * @param mood The mood the edit or view
         */
        fun gotoMood(mood: Mood)
        /**
         * Open the mood activity for a mood
         * @param id The id of the mood the edit or view
         */
        fun gotoEditMood(id: String)
        /**
         * Update the list of displayed moods
         * @param moods The new list of moods
         */
        fun refreshMoods(moods: ArrayList<Mood>)
    }

    /**
     * The history prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Get a filtered list of moods
         * @param emotion The emotion to filter for
         * @return the filtered list of moods
         */
        fun fetchMoods(emotion: String): ArrayList<Mood>
        /**
         * Get the full list of moods
         * @return the full list of moods
         */
        fun fetchMoods(): ArrayList<Mood>
        /**
         * Initializes a new mood, and tells the view to go to the mood activity
         * @param location The current location of the user
         */
        fun createMood(location: Location?)
        /**
         * Adds a new mood to the database
         * @param mood The new mood to add
         */
        fun addMood(mood: Mood)
        /**
         * Tells the view to open a mood
         * @param mood The mood to edit or view
         */
        fun editMood(mood: Mood)
        /**
         * Update an existing mood in the database
         * @param mood The mood to update
         */
        fun updateMood(mood: Mood)
        /**
         * Delete an existing mood from the database
         * @param mood The mood to delete
         */
        fun deleteMood(mood: Mood)
        /**
         * Filter the displayed moods
         * @param emotion The emotion to filter for
         */
        fun refreshMoods(emotion: String)
        /**
         * Remove the filter for existing moods
         */
        fun refreshMoods()
    }
}
