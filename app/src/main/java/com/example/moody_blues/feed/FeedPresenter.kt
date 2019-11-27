package com.example.moody_blues.feed

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FeedPresenter(private val view: FeedContract.View) : FeedContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    override fun gotoMap() {
        view.gotoMap()
    }

    override fun fetchFeed() {
        MainScope().launch {
            AppManager.fetchRequests()
            AppManager.fetchFeed()
        }
    }

    override fun getFeed(): ArrayList<Mood> {
        fetchFeed()
        val moods = AppManager.getFeed()
        return moods
    }
}
