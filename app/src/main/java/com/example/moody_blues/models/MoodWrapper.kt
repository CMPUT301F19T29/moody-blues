package com.example.moody_blues.models

data class MoodWrapper (
        var id: String? = null,
        var location_lat: Double? = null,
        var location_lon: Double? = null,
        var date_string: String? = null,
        var reason_text: String? = null,
        var reason_image: String? = null,
        var social: Int? = null,
        var emotion: Int? = null,
        var showLocation: Boolean? = null
)