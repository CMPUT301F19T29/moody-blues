package com.example.moody_blues.map

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
     * Select the location to be used in the mood
     */
    override fun selectLocation() {
        TODO("clicking on map should select that location to be used in the mood")
    }
}
