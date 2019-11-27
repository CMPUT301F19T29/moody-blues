package com.example.moody_blues.mood

import android.graphics.Bitmap
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * The non-toolkit logic for the mood activity
 */
class MoodPresenter(private val view: MoodContract.View) : MoodContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        view.presenter = this
    }

    /**
     * Tells the view to change its background color according to the emotion
     * @param emotion The index of the emotion selected
     */
    override fun onSelectEmotion(emotion: Int) {
        view.changeBgColor(Mood.EMOTION_COLORS[emotion])
    }

    /**
     * Callback when a social reason is selected
     * @param social The index of the social reason
     */
    override fun onSelectSocial(social: Int) {
    }

    /**
     * Update the values for a mood
     * @param mood The mood to update
     * @param emotion The new emotion id
     * @param social The new social id
     * @param reasonText The new reason text
     * @param showLocation Whether to show the location or not
     */
    override fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean) {
        mood.emotion = emotion
        mood.social = social
        mood.reasonText = reasonText
        mood.showLocation = showLocation
    }

    /**
     * Check if the reason text is valid
     */
    override fun verifyMoodFields(reasonText: String) {
        if (reasonText.length > 20 || reasonText.split(" ").size > 3)
            view.showVerifyError()
        else
            view.preBacktoHistory()
    }

    /**
     * Add a new mood
     * @param mood The new mood
     */
    override fun addMood(mood: Mood) {
        MainScope().launch {
            AppManager.addMood(mood)
            view.backtoHistory()
        }
    }

    /**
     * Edit an existing mood
     * @param mood The mood to edit
     */
    override fun editMood(mood: Mood) {
        MainScope().launch {
            AppManager.editMood(mood)
            view.backtoHistory()
        }
    }

    /**
     * Set the photo of the mood
     * @param bitmap The bitmap of the thumbnail
     * @param photo The path to the full image
     */
    override fun setPhoto(bitmap: Bitmap?, photo: File?) {
        view.changePhoto(bitmap, photo)
    }

}
