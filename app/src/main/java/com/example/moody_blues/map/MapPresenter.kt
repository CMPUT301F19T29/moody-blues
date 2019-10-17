package com.example.moody_blues.map

class MapPresenter(val mapView: MapContract.View) : MapContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        mapView.presenter = this
    }

    override fun start() {
    }
}
