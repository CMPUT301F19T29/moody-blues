package com.example.moody_blues.mood

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood

/**
 * The presenter for the mood activity
 */
class MoodPresenter(private val view: MoodContract.View) : MoodContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Confirm adding or editing a mood
     */
    override fun confirmMood(mood: Mood) {
        view.backtoHistory()
    }

}
