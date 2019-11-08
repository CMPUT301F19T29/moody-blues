package com.example.moody_blues.mood

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
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
//    var color = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)
        mood = this.intent.extras?.getSerializable(HistoryView.INTENT_MOOD) as Mood
        title = "New Mood"


        // Emotional state spinner stuff

        val emotionalStates = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val colors = arrayOf(Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, Color.LTGRAY, Color.MAGENTA)
        val emotionField = findViewById<Spinner>(R.id.mood_emotion_field)
        val color : Int
        if (emotionField != null) {
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, emotionalStates)
            emotionField.adapter = arrayAdapter

            emotionField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    emotionalStates[position]
//                    color = position
//                    parent.getChildAt(position).setBackgroundColor(colors[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Social spinner stuff

        val socialSituations = arrayOf("None", "Alone", "With one other person", "With two to several people", "With a group")
        val socialField = findViewById<Spinner>(R.id.mood_social_field)
        if (socialField != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, socialSituations)
            socialField.adapter = arrayAdapter

            socialField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    socialSituations[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

//        val view = this.window.decorView
//        view.setBackgroundColor(color)

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        val mood = intent.getSerializableExtra(INTENT_MOOD) as Mood
        // make buttons for mood
        val confirmButton: Button = findViewById(R.id.mood_save_button)

        // value fields
        val dateField: TextView = findViewById(R.id.mood_date_field)
//        val emotionField: Spinner = findViewById(R.id.mood_emotion_field)
//        val socialField: Spinner = findViewById(R.id.mood_social_field)
        val reasonField: TextView = findViewById(R.id.mood_reason_field)
        val locationField: TextView = findViewById(R.id.mood_location_field)

        dateField.text = mood.getDate()
//        emotionField.text = mood.getEmotion()
//        socialField.text = mood.getSocial()
        reasonField.text = mood.getReasonText()
        //locationField.text = mood.getLocation()

        // confirm button
        confirmButton.setOnClickListener {
            mood.setDate(dateField.text.toString())
            mood.setEmotion(emotionField.getSelectedItem().toString())
            mood.setSocial(socialField.getSelectedItem().toString())
            mood.setReasonText(reasonField.text.toString())
            mood.setLocation(locationField.text.toString())

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

