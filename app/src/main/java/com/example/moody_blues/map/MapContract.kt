package com.example.moody_blues.map
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood
import com.google.android.gms.maps.model.LatLng

/**
 * The contract between the map view and the presenter
 */
interface MapContract {
    /**
     * The map view
     */
    interface View : BaseView<Presenter> {
        /**
         * Transition to the mood page
         */
        fun gotoMood()

        fun getLocation()
        fun setDefaultLocation(location: Location?)
    }

    /**
     * The login prsenter
     */
    interface Presenter : BasePresenter {
        /**
         * Select the location to be used in the mood
         */
        fun selectLocation()

        fun fetchMoods(mapMode: Int): ArrayList<Mood>
        fun getLocation()
    }
}
