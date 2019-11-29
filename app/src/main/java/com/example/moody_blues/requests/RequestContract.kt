package com.example.moody_blues.requests
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Request

/**
 * Declares public functions of the request view and the presenter
 * Allows the user to make requests to other users and accept/reject others' requests
 */
interface RequestContract {
    /**
     * The request view
     */
    interface View : BaseView<Presenter> {
        fun updateList()
    }

    /**
     * The request presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Refresh the feed
         */
        fun refresh()

        /**
         * Request to follow the specified user
         * @param user The user to follow
         */
        fun requestFollow(user: String)
    }
}
