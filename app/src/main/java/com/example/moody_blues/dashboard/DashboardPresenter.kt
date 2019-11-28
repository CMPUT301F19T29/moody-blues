package com.example.moody_blues.dashboard

/**
 * The non-toolkit logic for the dashboard activity
 */
class DashboardPresenter(private val view: DashboardContract.View) : DashboardContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Tell the view to transition to the history activity
     */
    override fun gotoHistory() {
        view.gotoHistory()
    }

    /**
     * Tell the view to transition to the feed activity
     */
    override fun gotoFeed() {
        view.gotoFeed()
    }

    /**
     * Tell the view to transition to the requests activity
     */
    override fun gotoRequests() {
        view.gotoRequests()
    }
}
