package com.example.moody_blues.history

import com.example.moody_blues.models.Mood
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryPresenter(val historyView: HistoryContract.View) : HistoryContract.Presenter {

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

    override fun createNewMood() {
        val mood = Mood("", "", "", null, "", "")
        historyView.gotoMood(mood)
    }
}
