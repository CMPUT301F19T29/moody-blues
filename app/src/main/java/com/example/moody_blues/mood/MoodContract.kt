package com.example.moody_blues.mood
import android.graphics.Bitmap
import android.net.Uri
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood
import java.io.File

/**
 * Declares public functions of the mood view and the presenter
 * The mood allows the user to create or edit a mood
 */
interface MoodContract {
    /**
     * The mood view
     */
    interface View : BaseView<Presenter> {

        /**
         * Saves the data for the mood
         */
        fun preBacktoHistory()
        /**
         * Transition back to the history activity
         */
        fun backtoHistory()

        /**
         * Change the background color of the view
         * @param color The color to change to
         */
        fun changeBgColor(color: Int)

        /**
         * Confirm the mood matches the requirements
         * @return If all conditions are met
         */
        fun showVerifyError()

        /**
         * Change the photo of the mood
         * @param thumbnail The bitmap of the thumbnail
         * @param full The path to the full image
         */
        fun changePhoto(thumbnail: Bitmap?, full: File?)
    }

    /**
     * The mood presenter
     */
    interface Presenter : BasePresenter {
        /**
         * Tells the view to change its background color according to the emotion
         * @param emotion The index of the emotion selected
         */
        fun onSelectEmotion(emotion: Int)

        /**
         * Callback when a social reason is selected
         * @param social The index of the social reason
         */
        fun onSelectSocial(social: Int)

//        fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean, reasonImage: Uri?, reasonThumbnail: Uri?)

        /**
         * Update the values for a mood
         * @param mood The mood to update
         * @param emotion The new emotion id
         * @param social The new social id
         * @param reasonText The new reason text
         * @param showLocation Whether to show the location or not
         */
        fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean)

        /**
         * Check if the reason text is valid
         */
        fun verifyMoodFields(reasonText: String)

        /**
         * Add a new mood
         * @param mood The new mood
         */
        fun addMood(mood: Mood)

        /**
         * Edit an existing mood
         * @param mood The mood to edit
         */
        fun editMood(mood: Mood)

        /**
         * Set the photo of the mood
         * @param bitmap The bitmap of the thumbnail
         * @param photo The path to the full image
         */
        fun setPhoto(bitmap: Bitmap?, photo: File?)
    }
}
