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
        fun gotoHistory()
        fun gotoFeed()
        fun gotoRequests()
    }

    /**
     * The dashboard prsenter
     */
    interface Presenter : BasePresenter {
        fun gotoHistory()
        fun gotoFeed()
        fun gotoRequests()
    }
}
