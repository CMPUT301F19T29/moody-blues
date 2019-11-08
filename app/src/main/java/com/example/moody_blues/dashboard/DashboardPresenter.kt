package com.example.moody_blues.dashboard


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

    override fun gotoHistory() {
        dashboardView.gotoHistory()
    }

    override fun gotoFeed() {
        dashboardView.gotoFeed()
    }

    override fun gotoRequests() {
        dashboardView.gotoRequests()
    }
}
