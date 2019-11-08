package com.example.moody_blues.requests
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the request view and the presenter
 */
interface RequestContract {
    /**
     * The request view
     */
    interface View : BaseView<Presenter> {
        fun gotoFollowUser()
    }

    /**
     * The request prsenter
     */
    interface Presenter : BasePresenter {
        fun requestFollow(user: String)
    }
}
