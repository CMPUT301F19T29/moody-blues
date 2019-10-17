package com.example.moody_blues.feed

import com.example.moody_blues.map.MapContract

class FeedPresenter(val feedView: MapContract.View) : MapContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        feedView.presenter = this
    }

    override fun start() {
    }
}
