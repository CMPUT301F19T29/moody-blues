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
        /**
         * Open a popup to select a user to follow
         */
        fun gotoFollowUser()
    }

    /**
     * The request prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Request to follow the specified uesr
         * @param user The user to follow
         */
        fun requestFollow(user: String)
    }
}
