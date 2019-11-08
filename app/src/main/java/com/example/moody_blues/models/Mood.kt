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

/**
 * A model class for a mood
 * TODO: use private properties
 */
class Mood(
    var location: String? = null, // TODO: use Location class
    var date: String? = null, // TODO: use LocalTime class
    var reason_text: String? = null,
    var reason_image: Image? = null,
    var social: Int? = null,
    var emotion: Int? = null,
    var showLocation: Boolean = true,
    var id: String = ""
): Serializable {

    /**
     * Initialize a mood with the current time and given location
     * @param location The user's current location
     */
    constructor(location: String?) : this() {
        this.date = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)
                .format(LocalDateTime.now())
        this.location = location
    }

    /**
     * Get the date
     * @return The date as a string
     */
    fun getDateString(): String? {
        return this.date
    }

    /**
     * Get the reason
     * TODO: deprecate
     * @return The reason text
     */
    fun getReasonText(): String? {
        return this.reason_text
    }

    /**
     * Get the image
     * TODO: deprecate
     * @return the image
     */
    fun getReasonImage(): Image? {
        return this.reason_image
    }

    /**
     * Get the social reason
     * @return the social reason
     */
    fun getSocialString(): String {
        return SOCIAL_REASONS[this.social?: 0]
    }

    /**
     * Get the emotion string
     * @return the emotion string
     */
    fun getEmotionString(): String {
        return EMOTION_STATES[this.emotion?: 0]
    }

    /**
     * Get the emotion color
     * @return the emotion color
     */
    fun getColor(): Int {
        return EMOTION_COLORS[this.emotion?: 0]
    }

    /**
     * Set the reason text
     * TODO: deprecate
     * @param reason_text The new reason text
     */
    fun setReasonText(reason_text: String?) {
        this.reason_text = reason_text
    }

    /**
     * Set the reason image
     * TODO: deprecate
     * @param reason_image The new reason image
     */
    fun setReasonImage(reason_image: Image?) {
        this.reason_image = reason_image
    }

    /**
     * A class representing an emotion
     * TODO: will possibly be used in the future
     */
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
