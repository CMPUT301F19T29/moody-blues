package com.example.moody_blues.mood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.history.HistoryView.Companion.INTENT_MOOD
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood

class MoodView : AppCompatActivity(), MoodContract.View {
    override lateinit var presenter: MoodContract.Presenter
    private lateinit var mood: Mood
    private lateinit var confirmButton: Button
    private lateinit var dateField: TextView
    private lateinit var emotionField: TextView
    private lateinit var socialField: TextView
    private lateinit var reasonField: TextView
    private lateinit var locationField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)
        mood = this.intent.extras?.getSerializable(HistoryView.INTENT_MOOD) as Mood
        title = "New Mood"

        // Pass the view to the presenter
        presenter = MoodPresenter(this)
        mood = intent.getSerializableExtra(INTENT_MOOD) as Mood

        confirmButton = findViewById(R.id.mood_save_button)
        dateField = findViewById(R.id.mood_date_field)
        emotionField = findViewById(R.id.mood_emotion_field)
        socialField = findViewById(R.id.mood_social_field)
        reasonField = findViewById(R.id.mood_reason_field)
        locationField = findViewById(R.id.mood_location_field)

        dateField.text = mood.getDateString()
        emotionField.text = mood.getEmotion()
        socialField.text = mood.getSocial()
        reasonField.text = mood.getReasonText()
        //locationField.text = mood.getLocation()

        // confirm button
        confirmButton.setOnClickListener {
            if (!verifyMood()) {
                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

    private fun verifyMood(): Boolean {
        if (emotionField.text.isEmpty())
            return false
        if (reasonField.text.length > 20)
            return false
        if (reasonField.text.split(" ").size > 3)
            return false

        return true
    }

    companion object {
        const val INTENT_MOOD_RESULT = "mood_result"
    }

//    override fun gotoMap() {
//        val intent = Intent(this, MapView::class.java)
//        startActivity(intent)
//    }
}

