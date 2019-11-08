package com.example.moody_blues

/**
 * The presenter from which all other presenters should be inherited
 */
interface BasePresenter {

    /**
     * The call to make when the view associated with this presenter is started
     */
    fun start()
}
