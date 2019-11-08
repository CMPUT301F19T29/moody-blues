package com.example.moody_blues.models

import android.location.Location
import android.media.Image
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Mood(
    private var date: LocalDateTime? = null,
    private var reason_text: String? = null,
    private var reason_image: Image? = null,
    private var social: String? = null,
    private var emotion: String? = null,
    private var location: String? = null // todo: revert back to Location
) : Serializable {

    constructor(location: String?) : this() {
        this.date = LocalDateTime.now()
        this.location = location
    }

    fun getDate(): LocalDateTime? {
        return this.date
    }

    fun getDateString(): String? {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                .format(this.date)
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

    fun setDate(date: LocalDateTime?) {
        this.date = date
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
