package com.example.moody_blues.history

import android.location.Location
import android.util.Log
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
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
        return runBlocking {
            var moods = AppManager.getMoods().values
            moods.sortedByDescending { mood -> mood.getDate() }
            ArrayList<Mood>(moods)
        }
    }

    override fun createMood(location: Location?) {
        val mood = Mood(location.toString())
        historyView.gotoMood(mood)
    }

    override fun addMood(mood: Mood) {
        GlobalScope.launch{
            AppManager.addMood(mood)
            var moods = ArrayList<Mood>(AppManager.getMoods().values)
            historyView.refreshMoods(moods)
        }
    }

    override fun editMood(mood: Mood) {
        GlobalScope.launch {
            historyView.gotoEditMood(mood.id)
        }
    }

    override fun updateMood(mood: Mood) {
        GlobalScope.launch {
            AppManager.updateMood(mood.id, mood)
        }
    }

    override fun deleteMood(mood: Mood) {
        GlobalScope.launch {
            AppManager.deleteMood(mood.id)
        }
    }

    override fun refreshMoods(emotion: String) {
        historyView.refreshMoods(AppManager.getFilteredUserMoods(emotion))
    }

    override fun refreshMoods() {
        historyView.refreshMoods(AppManager.getUserMoods())
    }
}
