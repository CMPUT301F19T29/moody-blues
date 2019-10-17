package com.example.moody_blues.history

class HistoryPresenter(val historyView: HistoryContract.View) : HistoryContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        historyView.presenter = this
    }

    override fun start() {
    }
}
