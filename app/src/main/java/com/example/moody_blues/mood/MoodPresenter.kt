package com.example.moody_blues.mood

import android.graphics.Bitmap
import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * The presenter for the mood activity
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

    override fun onSelectEmotion(emotion: Int) {
        view.changeBgColor(Mood.EMOTION_COLORS[emotion])
    }

    override fun onSelectSocial(social: Int) {
    }

    override fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean, reasonImage: String?) {
        mood.emotion = emotion
        mood.social = social
        mood.reason_text = reasonText
        mood.showLocation = showLocation
        mood.reason_image_url = reasonImage
    }

    override fun verifyMoodFields(reasonText: String) {
        if (reasonText.length > 20 || reasonText.split(" ").size > 3)
            view.showVerifyError()
        else
            view.preBacktoHistory()
    }


    override fun addMood(mood: Mood) {
        MainScope().launch {
            AppManager.addMood(mood)
            view.backtoHistory()
        }
    }

    override fun editMood(mood: Mood) {
        MainScope().launch {
            AppManager.editMood(mood)
            view.backtoHistory()
        }
    }

    override fun setPhoto(bitmap: Bitmap?) {
        view.changePhoto(bitmap)
    }

}
