package com.example.moody_blues.feed
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

/**
 * The contract between the feed view and the presenter
 */
interface FeedContract {
    /**
     * The feed view
     */
    interface View : BaseView<Presenter> {
        fun gotoMap()
    }

    /**
     * The feed presenter
     */
    interface Presenter : BasePresenter {
        fun fetchFeed()
        fun getFeed() : ArrayList<Mood>
        fun gotoMap()
    }
}
