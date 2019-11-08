package com.example.moody_blues.dashboard

/**
 * This is the presenter for the dashboard activity
 */
class DashboardPresenter(val dashboardView: DashboardContract.View) : DashboardContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        dashboardView.presenter = this
    }

    override fun start() {
    }

    /**
     * Tell the view to transition to the history activity
     */
    override fun gotoHistory() {
        dashboardView.gotoHistory()
    }

    /**
     * Tell the view to transition to the feed activity
     */
    override fun gotoFeed() {
        dashboardView.gotoFeed()
    }

    /**
     * Tell the view to transition to the requests activity
     */
    override fun gotoRequests() {
        dashboardView.gotoRequests()
    }
}
