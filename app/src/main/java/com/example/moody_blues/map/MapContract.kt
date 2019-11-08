package com.example.moody_blues.map
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView

/**
 * The contract between the map view and the presenter
 */
interface MapContract {
    /**
     * The map view
     */
    interface View : BaseView<Presenter> {
        fun gotoMood()
    }

    /**
     * The login prsenter
     */
    interface Presenter : BasePresenter {
        fun selectLocation()
    }
}
