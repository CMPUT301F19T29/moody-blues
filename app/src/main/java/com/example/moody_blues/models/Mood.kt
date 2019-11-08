package com.example.moody_blues.models

import android.graphics.Color
import android.location.Location
import android.media.Image
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList

class Mood(
    var id: String = "",
    private var date: LocalDateTime? = null,
    private var reason_text: String? = null,
    private var reason_image: Image? = null,
    private var social: Int? = null,
    private var emotion: Int? = null,
    private var location: String? = null, // todo: revert back to Location
    private var showLocation: Boolean = true
) : Serializable {

    constructor(location: String?) : this() {
        this.date = LocalDateTime.now()
        this.location = location
    }

    fun getDate(): LocalDateTime? {
        return this.date
    }

    fun getDateString(): String? {
        return if (this.date == null){
            null
        }
        else{
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                    .format(this.date)
        }
    }

    fun getReasonText(): String? {
        return this.reason_text
    }

    fun getReasonImage(): Image? {
        return this.reason_image
    }

    fun getSocial(): String {
        return SOCIAL_REASONS[this.getSocialID()]
    }

    fun getSocialID(): Int {
        return this.social?: 0
    }

    fun getEmotion(): String {
        return EMOTION_STATES[this.getEmotionID()]
    }

    fun getColor(): Int {
        return EMOTION_COLORS[this.getEmotionID()]
    }

    fun getEmotionID(): Int {
        return this.emotion?: 0
    }

    fun getLocation(): String? {
        return this.location
    }

    fun getShowLocation(): Boolean {
        return this.showLocation
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

    fun setSocial(social: Int?) {
        this.social = social
    }

    fun setEmotion(emotion: Int?) {
        this.emotion = emotion
    }

    fun setLocation(location: String?) {
        this.location = location
    }

    fun setShowLocation(showLocation: Boolean) {
        this.showLocation = showLocation
    }

    private class Emotion(
            var text: String,
            var color: Int
    )

    companion object {
//        private val emotions: ArrayList<Emotion> = ArrayList()
        val EMOTION_STATES: Array<String> = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val EMOTION_COLORS: Array<Int> = arrayOf(Color.GREEN, Color.parseColor("#33FFF4"), Color.YELLOW, Color.parseColor("#FF6D66"), Color.LTGRAY, Color.parseColor("#FE9DFF"))
        val SOCIAL_REASONS: Array<String> = arrayOf("None", "Alone", "With one other person", "With two to several people", "With a group")

        init {
//            emotions.add(Emotion("\uD83D\uDE0E Happy", Color.GREEN))
//            emotions.add(Emotion("\uD83D\uDE20 Upset", Color.parseColor("#33FFF4")))
//            emotions.add(Emotion("\uD83D\uDE06 Excited", Color.YELLOW))
//            emotions.add(Emotion("\uD83D\uDE24 Agitated", Color.parseColor("#FF6D66")))
//            emotions.add(Emotion("\uD83D\uDE10 Bored", Color.LTGRAY))
//            emotions.add(Emotion("\uD83E\uDD14 Uncertain", Color.parseColor("#FE9DFF")))
        }
    }

}
