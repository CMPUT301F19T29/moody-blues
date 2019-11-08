package com.example.moody_blues.dashboard
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the dashboard view and the presenter
 */
interface DashboardContract {
    /**
     * The dashboard view
     */
    interface View : BaseView<Presenter> {
        /**
         * Transition to the history activity
         */
        fun gotoHistory()
        /**
         * Transition to the feed activity
         */
        fun gotoFeed()
        /**
         * Transition to the requests activity
         */
        fun gotoRequests()
    }

    /**
     * The dashboard prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Tell the view to transition to the history activity
         */
        fun gotoHistory()
        /**
         * Tell the view to transition to the feed activity
         */
        fun gotoFeed()
        /**
         * Tell the view to transition to the requests activity
         */
        fun gotoRequests()
    }
}
