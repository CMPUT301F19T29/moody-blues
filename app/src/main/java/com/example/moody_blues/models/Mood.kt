package com.example.moody_blues.models

import android.location.Location
import android.media.Image
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Mood(
    private var date: String? = null,
    private var time: String? = null,
    private var reason_text: String? = null,
    private var reason_image: Image? = null,
    private var social: String? = null,
    private var emotion: String? = null,
    private var location: String? = null // todo: revert back to Location
) : Serializable {

    constructor(_location: String?) : this() {
        val calendar = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CANADA)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.CANADA)
        this.date = dateFormat.format(calendar)
        this.time = timeFormat.format(calendar)
        this.location = _location
    }

    fun getDate(): String? {
        return this.date
    }

    fun getTime(): String? {
        return this.time
    }

    fun getReasonText(): String? {
        return this.reason_text
    }

    fun getReasonImage(): Image? {
        return this.reason_image
    }

    fun getSocial(): String? {
        return this.social
    }

    fun getEmotion(): String? {
        return this.emotion
    }

    fun getLocation(): String? {
        return this.location
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun setTime(time: String?) {
        this.time = time
    }

    fun setReasonText(reason_text: String?) {
        this.reason_text = reason_text
    }

    fun setReasonImage(reason_image: Image?) {
        this.reason_image = reason_image
    }

    fun setSocial(social: String?) {
        this.social = social
    }

    fun setEmotion(emotion: String?) {
        this.emotion = emotion
    }

    fun setLocation(location: String?) {
        this.location = location
    }

}
