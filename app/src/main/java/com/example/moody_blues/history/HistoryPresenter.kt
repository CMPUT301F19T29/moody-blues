package com.example.moody_blues.history

import android.location.Location
import android.util.Log
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.*
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
        return runBlocking {
            var moods : Collection<Mood> = AppManager.getFilteredUserMoods(emotion).values
            moods.sortedByDescending { mood -> mood.getDate() }
            ArrayList<Mood>(moods)
        }

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
        MainScope().launch {
            AppManager.addMood(mood)
            var moods = ArrayList<Mood>(AppManager.getMoods().values)
            historyView.refreshMoods(moods)
        }
    }

    override fun editMood(mood: Mood) {
        MainScope().launch {
            historyView.gotoEditMood(mood.id)
        }
    }

    override fun updateMood(mood: Mood) {
        MainScope().launch{
            AppManager.updateMood(mood.id, mood)
        }
    }

    override fun deleteMood(mood: Mood) {
        MainScope().launch{
            AppManager.deleteMood(mood.id)
        }
    }

    override fun refreshMoods(emotion: String) {
        MainScope().launch{
            var moods = AppManager.getFilteredUserMoods(emotion).values.sortedByDescending { mood -> mood.getDate() }
            historyView.refreshMoods(ArrayList<Mood>(moods))
        }
    }

    override fun refreshMoods() {
        suspend {
            AppManager.refreshMoods()
            var moods = AppManager.getMoods().values.sortedByDescending { mood -> mood.getDate() }
            historyView.refreshMoods(ArrayList<Mood>(moods))
        }
    }
}
