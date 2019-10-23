package com.example.moody_blues.feed

class FeedPresenter(val feedView: FeedContract.View) : FeedContract.Presenter {

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