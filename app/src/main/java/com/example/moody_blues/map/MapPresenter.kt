package com.example.moody_blues.map

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import java.nio.channels.FileChannel

class MapPresenter(private val view: MapContract.View) : MapContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    override fun fetchMoods(mapMode: Int): ArrayList<Mood> {
        return if (mapMode == 1)
            ArrayList(AppManager.getUserMoods(null).values)
        else
            AppManager.getFeed()
    }

    override fun getLocation() {
        view.getLocation()
    }

    /**
     * Select the location to be used in the mood
     */
    override fun selectLocation() {
        TODO("clicking on map should select that location to be used in the mood")
    }
}
