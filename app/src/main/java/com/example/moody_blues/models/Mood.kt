package com.example.moody_blues.models

import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * A model class for a mood
 * TODO: use private properties
 */
class Mood(
        var id: String = "",
        var location: LatLng? = null,
        var date: LocalDateTime = LocalDateTime.now(),
        var reason_text: String? = null,
        var reason_image: Bitmap? = null,
        var social: Int = 0,
        var emotion: Int = 0,
        var showLocation: Boolean = true
): Parcelable {

    constructor(location: Location?): this() {
        this.location = if (location == null) null else LatLng(location!!.latitude, location!!.longitude)
    }

    constructor(parcel: Parcel): this(
            parcel.readString()?: "",
            parcel.readParcelable(LatLng::class.java.classLoader),
            parcel.readSerializable() as LocalDateTime,
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()
    )

    constructor(wrapper: MoodWrapper): this(
            wrapper.id?: "",
            LatLng(wrapper.location_lat?: 0.0, wrapper.location_lon?: 0.0),
            LocalDateTime.parse(wrapper.date_string, DATE_FORMAT),
            wrapper.reason_text,
            wrapper.reason_image,
            wrapper.social?: 0,
            wrapper.emotion?: 0,
            wrapper.showLocation?: true
    )

    fun wrap(): MoodWrapper {
        return MoodWrapper(
                this.id,
                this.location?.latitude,
                this.location?.longitude,
                this.getDateString(),
                this.reason_text,
                this.reason_image,
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
        parcel.writeParcelable(location, flags)
        parcel.writeSerializable(date)
        parcel.writeString(reason_text)
        parcel.writeParcelable(reason_image, flags)
        parcel.writeInt(social)
        parcel.writeInt(emotion)
        parcel.writeByte(if (showLocation) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mood> {
        val EMOTION_STATES: Array<String> = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
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
