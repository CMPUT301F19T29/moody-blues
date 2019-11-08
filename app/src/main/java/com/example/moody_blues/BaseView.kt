package com.example.moody_blues

/**
 * The view from which all other views should be inherited.
 */
interface BaseView<T> {
    /**
     * The presenter to handle the logic that governs this view
     */
    var presenter: T

}
