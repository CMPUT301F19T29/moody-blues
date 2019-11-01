package com.example.moody_blues.models

import android.media.Image
import java.io.Serializable

class Mood (
    date: String,
    time: String,
    reason_text: String,
    reason_image: Image? = null,
    social: String,
    emotion: String) : Serializable {

    var date: String = date
        get() = field
        set(value) { field = value}

    var time: String = time
        get() = field
        set(value) { field = value}

    var reason_text: String = reason_text
        get() = field
        set(value) { field = value}

    var reason_image: Image? = reason_image
        get() = field
        set(value) { field = value}

    var social: String = social
        get() = field
        set(value) { field = value}

    var emotion: String = emotion
        get() = field
        set(value) { field = value}
}
