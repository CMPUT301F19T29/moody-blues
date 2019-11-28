package com.example.moody_blues.map
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood
import com.google.android.gms.maps.model.LatLng

/**
 * Declares public functions of the map view and the presenter
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

        /**
         * Get the user's current location
         */
        fun getLocation()

        /**
         * Centers the map to a location
         * @param location The location to center on
         */
        fun setDefaultLocation(location: Location?)
    }

    /**
     * The login presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Gets the list of moods to show on the map
         * @return The list of moods to display
         */
        fun fetchMoods(mapMode: Int): ArrayList<Mood>

        /**
         * Tells the view to get the user's current location
         */
        fun getLocation()
    }
}
