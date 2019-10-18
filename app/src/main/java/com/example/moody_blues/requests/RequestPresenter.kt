package com.example.moody_blues.requests

class RequestPresenter(val requestView: RequestContract.View) : RequestContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        requestView.presenter = this
    }

    override fun start() {
    }
}
