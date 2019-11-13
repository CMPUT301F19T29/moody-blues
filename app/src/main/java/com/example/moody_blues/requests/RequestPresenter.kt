package com.example.moody_blues.requests

class RequestPresenter(private val view: RequestContract.View) : RequestContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Request to follow the specified uesr
     * @param user The user to follow
     */
    override fun requestFollow(user: String) {
        // checks username with firebase database for valid user to follow
        // exit popup

    }
}
