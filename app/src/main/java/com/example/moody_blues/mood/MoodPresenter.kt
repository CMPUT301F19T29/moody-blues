package com.example.moody_blues.mood

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood

class MoodPresenter(val moodView: MoodContract.View) : MoodContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        moodView.presenter = this
    }

    override fun start() {
    }

    override fun confirmMood(mood: Mood) {
        moodView.backtoHistory()
    }

}
