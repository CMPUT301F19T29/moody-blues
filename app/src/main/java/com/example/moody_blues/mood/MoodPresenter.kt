package com.example.moody_blues.mood

import com.example.moody_blues.map.MapContract

class MoodPresenter(val moodView: MapContract.View) : MapContract.Presenter {

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
}
