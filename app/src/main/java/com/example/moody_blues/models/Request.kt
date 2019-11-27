package com.example.moody_blues.models

/**
 * The model for a request to be stored in the database
 */
data class Request(var from: String = "", var to: String = "", var accepted: Boolean = false)
