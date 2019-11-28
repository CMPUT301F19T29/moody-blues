package com.example.moody_blues.feed
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

/**
 * Declares public functions of the feed view and the presenter
 */
interface FeedContract {
    /**
     * The feed view
     */
    interface View : BaseView<Presenter> {
        /**
         * Transition the map activity
         */
        fun gotoMap()

        /**
         * Refresh the feed
         * @param feed The new feed to display
         */
        fun refreshFeed(feed: ArrayList<Mood>)
    }

    /**
     * The feed presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Refresh the feed
         */
        fun refresh()

        /**
         * Fetches the feed from the database
         */
        fun fetchFeed()

        /**
         * Get the local feed from memory
         * @return list of moods for the feed
         */
        fun getFeed() : ArrayList<Mood>

        /**
         * Tell the view to transition to the map activity
         */
        fun gotoMap()
    }
}
