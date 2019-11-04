package com.example.moody_blues.models

import android.location.Location
import android.media.Image
import java.io.Serializable
import java.util.*

class Mood(
    var date: Date? = Calendar.getInstance().time,
    var reason_text: String? = null,
    var reason_image: Image? = null,
    var social: String? = null,
    var emotion: String? = null,
    var location: String? = null // todo: revert back to Location
) : Serializable {

    constructor(_location: String?) : this(location = _location)

}
