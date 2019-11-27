package com.example.moody_blues.requests
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Request

/**
 * Declares public functions of the request view and the presenter
 */
interface RequestContract {
    /**
     * The request view
     */
    interface View : BaseView<Presenter> {
        fun restartActivity()
    }

    /**
     * The request presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Request to follow the specified user
         * @param user The user to follow
         */
        fun requestFollow(user: String)
    }
}
