package com.example.moody_blues.feed
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the feed view and the presenter
 */
interface FeedContract {
    /**
     * The feed view
     */
    interface View : BaseView<Presenter> {}

    /**
     * The feed prsenter
     */
    interface Presenter : BasePresenter {}
}
