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

    override fun fetchMoods(): ArrayList<Mood> {
        return AppManager.getUserMoods()
    }

    override fun createMood(location: Location?) {
        val mood = Mood(location.toString())
        historyView.gotoMood(mood)
    }

    override fun addMood(mood: Mood) {
        AppManager.addUserMood(mood)
    }
}
