package com.example.moody_blues.history

import android.location.Location
import android.util.Log
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

    override fun fetchMoods(emotion: String): ArrayList<Mood> {
        return AppManager.getFilteredUserMoods(emotion)
    }

    override fun fetchMoods(): ArrayList<Mood> {
        return AppManager.getUserMoods()
    }

    override fun createMood(location: Location?) {
        val mood = Mood(location.toString())
        historyView.gotoMood(mood)
    }

    override fun addMood(mood: Mood) {
        AppManager.addUserMood(mood)
        historyView.refreshMoods(AppManager.getUserMoods())
    }

    override fun editMood(pos: Int) {
        historyView.gotoEditMood(pos)
    }

    override fun updateMood(mood: Mood, pos: Int) {
        AppManager.updateMood(mood, pos)
    }

    override fun deleteMood(mood: Mood) {
        AppManager.deleteMood(mood)
    }

    override fun refreshMoods(emotion: String) {
        historyView.refreshMoods(AppManager.getFilteredUserMoods(emotion))
    }

    override fun refreshMoods() {
        historyView.refreshMoods(AppManager.getUserMoods())
    }
}
