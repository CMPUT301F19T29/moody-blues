package com.example.moody_blues.models

/**
 * A database-safe wrapper for the mood class
 */
data class MoodWrapper (
        var locationLat: Double? = null,
        var locationLon: Double? = null,
        var date_string: String? = null,
        var reasonText: String? = null,
        var reasonImageThumbnail: String? = null,
        var reasonImageFull: String? = null,
        var social: Int? = null,
        var emotion: Int? = null,
        var showLocation: Boolean? = null
)