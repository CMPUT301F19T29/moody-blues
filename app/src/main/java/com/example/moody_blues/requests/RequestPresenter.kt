package com.example.moody_blues.requests

import com.example.moody_blues.AppManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
        MainScope().launch {
            AppManager.addRequest(user)
            AppManager.fetchRequests()
            view.updateList()
        }
    }
}
