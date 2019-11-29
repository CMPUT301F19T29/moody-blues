package com.example.moody_blues.feed

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * The non-toolkit logic for the feed activity
 */
class FeedPresenter(private val view: FeedContract.View) : FeedContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
        refresh()
    }

    /**
     * Refresh the feed
     */
    override fun refresh() {
        MainScope().launch {
            AppManager.fetchRequests()
            AppManager.fetchFeed()
            view.refreshFeed(AppManager.getFeed())
        }
    }

    /**
     * Tell the view to transition to the map activity
     */
    override fun gotoMap() {
        view.gotoMap()
    }

    /**
     * Fetches the feed from the database
     */
    override fun fetchFeed() {
        MainScope().launch {
            AppManager.fetchRequests()
            AppManager.fetchFeed()
        }
    }

    /**
     * Get the local feed from memory
     * @return list of moods for the feed
     */
    override fun getFeed(): ArrayList<Mood> {
        fetchFeed()
        return AppManager.getFeed()
    }
}
