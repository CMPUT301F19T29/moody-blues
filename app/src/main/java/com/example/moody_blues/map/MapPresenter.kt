package com.example.moody_blues.map

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import java.nio.channels.FileChannel

/**
 * The non-toolkit logic for the map activity
 */
class MapPresenter(private val view: MapContract.View) : MapContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Gets the list of moods to show on the map
     * @return The list of moods to display
     */
    override fun fetchMoods(mapMode: Int): ArrayList<Mood> {
        return if (mapMode == 1)
            ArrayList(AppManager.getUserMoods(null).values)
        else
            AppManager.getFeed()
    }

    /**
     * Tells the view to get the user's current location
     */
    override fun getLocation() {
        view.getLocation()
    }
}
