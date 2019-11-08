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
    var location: String? = null, // todo: revert back to Location
    var date: String? = null,
    var reason_text: String? = null,
    var reason_image: Image? = null,
    var social: Int? = null,
    var emotion: Int? = null,
    var showLocation: Boolean = true,
    var id: String = ""
): Serializable {
    constructor(location: String?) : this() {
        this.date = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                .format(LocalDateTime.now())
        this.location = location
    }

    fun getDateString(): String? {
        return this.date
    }

    fun getReasonText(): String? {
        return this.reason_text
    }

    fun getReasonImage(): Image? {
        return this.reason_image
    }

    fun getSocialString(): String {
        return SOCIAL_REASONS[this.social?: 0]
    }

    fun getEmotionString(): String {
        return EMOTION_STATES[this.emotion?: 0]
    }

    fun getColor(): Int {
        return EMOTION_COLORS[this.emotion?: 0]
    }

    fun setReasonText(reason_text: String?) {
        this.reason_text = reason_text
    }

    fun setReasonImage(reason_image: Image?) {
        this.reason_image = reason_image
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
