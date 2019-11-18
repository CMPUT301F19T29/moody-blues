package com.example.moody_blues.models

import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * A model class for a mood
 * TODO: use private properties
 */
class Mood(
        var id: String = "",
        var username: String = "",
        var location: LatLng? = null,
        var date: LocalDateTime = LocalDateTime.now(),
        var reason_text: String? = null,
        var reason_image_thumbnail: String? = null,
        var reason_image_full: String? =null,
        var social: Int = 0,
        var emotion: Int = 0,
        var showLocation: Boolean = true
): Parcelable {

    constructor(location: Location?): this() {
        this.location = if (location == null) null else LatLng(location.latitude, location.longitude)
    }

    constructor(parcel: Parcel): this(
            parcel.readString()?: "",
            parcel.readString()?: "",
            parcel.readParcelable(LatLng::class.java.classLoader),
            parcel.readSerializable() as LocalDateTime,
            parcel.readString(),
//            parcel.readParcelable(Uri::class.java.classLoader),
//            parcel.readParcelable(Uri::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()
    )

    constructor(wrapper: MoodWrapper, id: String, username: String): this(
            id,
            username,
            null,
            LocalDateTime.parse(wrapper.date_string, DATE_FORMAT),
            wrapper.reason_text,
//            Uri.parse(wrapper.reason_image_thumbnail),
//            Uri.parse(wrapper.reason_image_full),
            wrapper.reason_image_thumbnail,
            wrapper.reason_image_full,
            wrapper.social?: 0,
            wrapper.emotion?: 0,
            wrapper.showLocation?: true
    ) {
        this.location = if (wrapper.location_lat == null) null else LatLng(wrapper.location_lat!!, wrapper.location_lon!!)
    }

    fun wrap(): MoodWrapper {
        return MoodWrapper(
                this.location?.latitude,
                this.location?.longitude,
                this.getDateString(),
                this.reason_text,
                this.reason_image_thumbnail.toString(),
                this.reason_image_full.toString(),
                this.social,
                this.emotion,
                this.showLocation
        )
    }

    /**
     * Get the date
     * @return The date as a string
     */
    fun getDateString(): String {
        return DATE_FORMAT.format(this.date)
    }

    /**
     * Get the social reason
     * @return the social reason
     */
    fun getSocialString(): String {
        return SOCIAL_REASONS[this.social]
    }

    /**
     * Get the emotion string
     * @return the emotion string
     */
    fun getEmotionString(): String {
        return EMOTION_STATES[this.emotion]
    }

    /**
     * Get the emotion color
     * @return the emotion color
     */
    fun getColor(): Int {
        return EMOTION_COLORS[this.emotion]
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeParcelable(location, flags)
        parcel.writeSerializable(date)
        parcel.writeString(reason_text)
        parcel.writeString(reason_image_thumbnail)
        parcel.writeString(reason_image_full)
//        parcel.writeParcelable(reason_image_thumbnail, flags)
//        parcel.writeParcelable(reason_image_full, flags)
        parcel.writeInt(social)
        parcel.writeInt(emotion)
        parcel.writeByte(if (showLocation) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mood> {
        val EMOTION_STATES: Array<String> = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val EMOTION_FILTERS: Array<String> = arrayOf("❌ No filter", "\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val EMOTION_COLORS: Array<Int> = arrayOf(Color.GREEN, Color.parseColor("#33FFF4"), Color.YELLOW, Color.parseColor("#FF6D66"), Color.LTGRAY, Color.parseColor("#FE9DFF"))
        val SOCIAL_REASONS: Array<String> = arrayOf("None", "Alone", "With one other person", "With two to several people", "With a group")
        private val DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)

        override fun createFromParcel(parcel: Parcel): Mood {
            return Mood(parcel)
        }

        override fun newArray(size: Int): Array<Mood?> {
            return arrayOfNulls(size)
        }
    }

}
