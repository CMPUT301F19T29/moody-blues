package com.example.moody_blues.history
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

/**
 * Declares public functions of the history view and the presenter
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
         * @param mood The id of the mood the edit or view
         */
        fun gotoEditMood(mood: Mood)
        /**
         * Update the list of displayed moods
         * @param moods The new list of moods
         */
        fun refreshMoods(moods: ArrayList<Mood>)

        /**
         *
         */
        fun getLocation()

        /**
         * Transition to the map activity
         */
        fun gotoMap()
    }

    /**
     * The history prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Refresh the history
         */
        fun refresh()

        /**
         * Get a filtered list of moods
         * @param emotion The emotion to filter for
         * @return the filtered list of moods
         */
        @Deprecated("use refreshMoods() instead")
        fun fetchMoods(emotion: String): ArrayList<Mood>

        /**
         * Get the full list of moods
         * @return the full list of moods for the history
         */
        fun fetchMoods(filter: Int): ArrayList<Mood>

        /**
         * Get the list of moods in memory
         * @param filter The index of the filter selection. 0 = no filter
         */
        fun refreshMoods(filter: Int)

        /**
         * Initializes a new mood, and tells the view to go to the mood activity
         * @param location The current location of the user
         */
        fun createMood(location: Location?)

        /**
         * Initiate steps to create a new mood
         */
        fun onAddMood()

        /**
         * Tells the view to open a mood
         * @param mood The mood to edit or view
         */
        fun onEditMood(mood: Mood)

        /**
         * Delete an existing mood from the database
         * @param mood The mood to delete
         */
        fun deleteMood(mood: Mood)

        /**
         * Tell the view to transition to the map activity
         */
        fun gotoMap()
    }
}
