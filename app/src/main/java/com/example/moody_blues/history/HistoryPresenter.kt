package com.example.moody_blues.history

import android.location.Location
import android.util.Log
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * This is the presenter for the history activity
 */
class HistoryPresenter(private val historyView: HistoryContract.View) : HistoryContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        historyView.presenter = this
    }

    override fun start() {
    }

    /**
     * Get a filtered list of moods
     * @param emotion The emotion to filter for
     * @return the filtered list of moods
     */
    override fun fetchMoods(emotion: String): ArrayList<Mood> {
        var moods : Collection<Mood> = AppManager.getFilteredUserMoods(emotion).values
        moods.sortedByDescending { mood -> mood.date }
        return ArrayList<Mood>(moods)
    }

    /**
     * Get the full list of moods
     * @return the full list of moods
     */
    override fun fetchMoods(): ArrayList<Mood> {
        var moods : Collection<Mood> = AppManager.getMoods().values
        moods.sortedByDescending { mood -> mood.date }
        return ArrayList<Mood>(moods)
    }

    /**
     * Initializes a new mood, and tells the view to go to the mood activity
     * @param location The current location of the user
     */
    override fun createMood(location: Location?) {
        val mood = Mood(location.toString())
        historyView.gotoMood(mood)
    }

    /**
     * Adds a new mood to the database
     * @param mood The new mood to add
     */
    override fun addMood(mood: Mood) {
        MainScope().launch {
            AppManager.addMood(mood)
            var moods = ArrayList<Mood>(AppManager.getMoods().values)
            historyView.refreshMoods(moods)
        }
    }

    /**
     * Tells the view to open a mood
     * @param mood The mood to edit or view
     */
    override fun editMood(mood: Mood) {
        historyView.gotoEditMood(mood.id)
    }

    /**
     * Update an existing mood in the database
     * @param mood The mood to update
     */
    override fun updateMood(mood: Mood) {
        MainScope().launch{
            AppManager.updateMood(mood.id, mood)
            var moods = ArrayList<Mood>(AppManager.getMoods().values)
            historyView.refreshMoods(moods)
        }
    }

    /**
     * Delete an existing mood from the database
     * @param mood The mood to delete
     */
    override fun deleteMood(mood: Mood) {
        MainScope().launch{
            AppManager.deleteMood(mood.id)
            var moods = ArrayList<Mood>(AppManager.getMoods().values)
            historyView.refreshMoods(moods)
        }
    }

    /**
     * Filter the displayed moods
     * @param emotion The emotion to filter for
     */
    override fun refreshMoods(emotion: String) {
        MainScope().launch{
            var moods = AppManager.getFilteredUserMoods(emotion).values.sortedByDescending { mood -> mood.date }
            historyView.refreshMoods(ArrayList<Mood>(moods))
        }
    }

    /**
     * Remove the filter for existing moods
     */
    override fun refreshMoods() {
        MainScope().launch{
            var moods = AppManager.getMoods().values.sortedByDescending { mood -> mood.date }
            historyView.refreshMoods(ArrayList<Mood>(moods))
        }
    }

}
