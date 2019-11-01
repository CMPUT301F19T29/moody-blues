package com.example.moody_blues.mood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood

class MoodView : AppCompatActivity(), MoodContract.View {
    override lateinit var presenter: MoodContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        val mood = intent.getSerializableExtra("mood") as Mood
        // make buttons for mood
        val confirmButton: Button = findViewById(R.id.mood_save_button)
        // value fields
        val dateField: TextView = findViewById(R.id.mood_date_field)
        val timeField: TextView = findViewById(R.id.mood_time_field)
        val emotionField: TextView = findViewById(R.id.mood_emotion_field)
        val socialField: TextView = findViewById(R.id.mood_social_field)
        val reasonField: TextView = findViewById(R.id.mood_reason_field)
//        val locationField: TextView = findViewById(R.id.mood_location_field)

        // confirm button
        confirmButton.setOnClickListener {
            mood.date = dateField.text.toString()
            mood.time = timeField.text.toString()
            mood.emotion = emotionField.text.toString()
            mood.social = socialField.text.toString()
            mood.reason_text = reasonField.text.toString()
//            mood.location = locationField.text.toString()
            presenter.confirmMood()
        }

    }

    override fun backtoHistory() {
        finish()
    }

//    override fun gotoMap() {
//        val intent = Intent(this, MapView::class.java)
//        startActivity(intent)
//    }
}

