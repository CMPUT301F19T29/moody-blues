package com.example.moody_blues.mood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.history.HistoryView.Companion.INTENT_MOOD
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import org.w3c.dom.Text

class MoodView : AppCompatActivity(), MoodContract.View {
    override lateinit var presenter: MoodContract.Presenter
    private lateinit var mood: Mood

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)
        mood = this.intent.extras?.getSerializable(HistoryView.INTENT_MOOD) as Mood
        title = "New Mood"

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        val mood = intent.getSerializableExtra(INTENT_MOOD) as Mood
        // make buttons for mood
        val confirmButton: Button = findViewById(R.id.mood_save_button)

        // value fields
        val dateField: TextView = findViewById(R.id.mood_date_field)
        val emotionField: TextView = findViewById(R.id.mood_emotion_field)
        val socialField: TextView = findViewById(R.id.mood_social_field)
        val reasonField: TextView = findViewById(R.id.mood_reason_field)
//        val locationField: TextView = findViewById(R.id.mood_location_field)

        dateField.text = mood.getDateString()
        emotionField.text = mood.getEmotion()
        socialField.text = mood.getSocial()
        reasonField.text = mood.getReasonText()
        //locationField.text = mood.getLocation()

        // confirm button
        confirmButton.setOnClickListener {
            mood.setEmotion(emotionField.text.toString())
            mood.setSocial(socialField.text.toString())
            mood.setReasonText(reasonField.text.toString())

            val intent = Intent()
            intent.putExtra(INTENT_MOOD_RESULT, mood)
            setResult(RESULT_OK, intent)

            presenter.confirmMood()
        }
    }

    override fun backtoHistory() {
        finish()
    }

    companion object {
        const val INTENT_MOOD_RESULT = "mood_result"
    }

//    override fun gotoMap() {
//        val intent = Intent(this, MapView::class.java)
//        startActivity(intent)
//    }
}

