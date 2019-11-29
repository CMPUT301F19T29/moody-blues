package com.example.moody_blues.models

import android.graphics.Color
import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import androidx.core.graphics.ColorUtils
import com.example.moody_blues.AppManager
import com.google.android.gms.maps.model.LatLng
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


/**
 * A model class for a mood
 * TODO: use private properties
 */
class Mood(
    var id: String = "",
    var username: String = "",
    var location: LatLng? = null,
    var date: Date? = Date(),
    var reasonText: String? = null,
    var reasonImageThumbnail: String? = null,
    var reasonImageFull: String? = null,
    var social: Int = 0,
    var emotion: Int = 0,
    var showLocation: Boolean = true
): Parcelable {
    constructor(location: Location?): this() {
        this.username = AppManager.getUsername()?: ""
        this.location = if (location == null) null else LatLng(location.latitude, location.longitude)
    }

    constructor(parcel: Parcel): this(
            parcel.readString()?: "",
            parcel.readString()?: "",
            parcel.readParcelable(LatLng::class.java.classLoader),
            parcel.readSerializable() as Date,
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
            wrapper.date,
            wrapper.reasonText,
//            Uri.parse(wrapper.reasonImageThumbnail),
//            Uri.parse(wrapper.reasonImageFull),
            wrapper.reasonImageThumbnail,
            wrapper.reasonImageFull,
            wrapper.social?: 0,
            wrapper.emotion?: 0,
            wrapper.showLocation?: true
    ) {
        this.location = if (wrapper.locationLat == null) null else LatLng(wrapper.locationLat!!, wrapper.locationLon!!)
    }

    /**
     * Converts the mood into a database-safe model
     * @return The model to store in the database
     */
    fun wrap(): MoodWrapper {
        return MoodWrapper(
                this.location?.latitude,
                this.location?.longitude,
                this.date,
                this.reasonText,
                this.reasonImageThumbnail,
                this.reasonImageFull,
                this.social,
                this.emotion,
                this.showLocation
        )
    }

    /**
     * Get the date description
     * @return The date as a string
     */
    fun getDateString(): String {
        return DATE_FORMAT.format(this.date!!)
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

    /**
     * Get the emotion color as a value between 0 and 1
     * @return the emotion color float
     */
    fun getColorWheel() : Float {
        val out = FloatArray(3)
        ColorUtils.colorToHSL(getColor(), out)
        return out[0]
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeParcelable(location, flags)
        parcel.writeSerializable(date)
        parcel.writeString(reasonText)
        parcel.writeString(reasonImageThumbnail)
        parcel.writeString(reasonImageFull)
//        parcel.writeParcelable(reasonImageThumbnail, flags)
//        parcel.writeParcelable(reasonImageFull, flags)
        parcel.writeInt(social)
        parcel.writeInt(emotion)
        parcel.writeByte(if (showLocation) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mood> {
        val EMOTION_STATES: Array<String> = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE24 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE2C Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val EMOTION_FILTERS: Array<String> = arrayOf("❌ No filter", "\uD83D\uDE0E Happy", "\uD83D\uDE24 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE2C Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val EMOTION_COLORS: Array<Int> = arrayOf(Color.parseColor("#22FF33"), Color.parseColor("#4466FF"), Color.parseColor("#CCCC33"), Color.parseColor("#EE6688"), Color.parseColor("#CC9944"), Color.parseColor("#FF99FF"))
        val SOCIAL_REASONS: Array<String> = arrayOf("   ", "Alone", "With another", "With a few", "With a group")
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd, h:mm a")

        override fun createFromParcel(parcel: Parcel): Mood {
            return Mood(parcel)
        }

        override fun newArray(size: Int): Array<Mood?> {
            return arrayOfNulls(size)
        }
    }

}
