package com.example.moody_blues.history

import android.location.Location
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*

/**
 * The non-toolkit logic for the history activity
 */
class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
        refresh()
    }

    /**
     * Refresh the history
     */
    override fun refresh() {
        MainScope().launch {
            AppManager.fetchMoods()
            view.refreshMoods(AppManager.getOrderedUserMoods(null))
        }
    }

    /**
     * Get a filtered list of moods
     * @param emotion The emotion to filter for
     * @return the filtered list of moods
     */
    override fun fetchMoods(emotion: String): ArrayList<Mood> {
        var moods : Collection<Mood> = AppManager.getFilteredUserMoods(emotion).values
        moods.sortedByDescending { mood -> mood.date }
        return ArrayList(moods)
    }

    /**
     * Get the full list of moods
     * @return the full list of moods
     */
    override fun fetchMoods(filter: Int): ArrayList<Mood> {
        val emotion = if (filter == 0) null else filter - 1
        return AppManager.getOrderedUserMoods(emotion)
    }

    /**
     * Get the list of moods in memory
     * @param filter The index of the filter selection. 0 = no filter
     */
    override fun refreshMoods(filter: Int) {
        view.refreshMoods(this.fetchMoods(filter))
    }

    /**
     * Initiate steps to create a new mood
     */
    override fun onAddMood() {
        view.getLocation()
    }

    /**
     * Initializes a new mood, and tells the view to go to the mood activity
     * @param location The current location of the user
     */
    override fun createMood(location: Location?) {
        val mood = Mood(location)
        view.gotoMood(mood)
    }

    /**
     * Tells the view to open a mood
     * @param mood The mood to edit or view
     */
    override fun onEditMood(mood: Mood) {
        view.gotoEditMood(mood)
    }

    /**
     * Delete an existing mood from the database
     * @param mood The mood to delete
     */
    override fun deleteMood(mood: Mood) {
        MainScope().launch{
            AppManager.deleteMood(mood.id)
            val moods = ArrayList<Mood>(AppManager.getMoods().values)
            view.refreshMoods(moods)
        }
    }

    /**
     * Tell the view to transition to the map activity
     */
    override fun gotoMap() {
        view.gotoMap()
    }
}
