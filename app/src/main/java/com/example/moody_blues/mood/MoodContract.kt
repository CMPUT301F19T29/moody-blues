package com.example.moody_blues.mood
import android.graphics.Bitmap
import android.net.Uri
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood
import java.io.File

/**
 * The contract between the mood view and the presenter
 */
interface MoodContract {
    /**
     * The mood view
     */
    interface View : BaseView<Presenter> {

        fun preBacktoHistory()
        /**
         * Transition back to the history activity
         */
        fun backtoHistory()
//        fun gotoMap()
        fun changeBgColor(color: Int)

        fun showVerifyError()

        fun changePhoto(bitmap: Bitmap?, photo: File?)
    }

    /**
     * The mood presenter
     */
    interface Presenter : BasePresenter {
        fun onSelectEmotion(emotion: Int)

        fun onSelectSocial(social: Int)

//        fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean, reasonImage: Uri?, reasonThumbnail: Uri?)

        fun setMoodFields(mood: Mood, emotion: Int, social: Int, reasonText: String, showLocation: Boolean)

        fun verifyMoodFields(reasonText: String)

        fun addMood(mood: Mood)

        fun editMood(mood: Mood)

        fun setPhoto(bitmap: Bitmap?, photo: File?)
    }
}
