package com.example.moody_blues.mood

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
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

//    var color = Color.WHITE
    private lateinit var confirmButton: Button
    private lateinit var dateField: TextView
    private lateinit var emotionField: Spinner
    private lateinit var socialField: Spinner
    private lateinit var reasonField: TextView
    private lateinit var locationField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)
        mood = this.intent.extras?.getSerializable(INTENT_MOOD) as Mood
        val flag = this.intent.getStringExtra(HistoryView.FLAG) as String

        if (flag == "add") {
            title = "New Mood"
        }
        else if (flag == "edit") {
            title = "Edit Mood"
        }

        confirmButton = findViewById(R.id.mood_save_button)
        dateField = findViewById(R.id.mood_date_field)
        emotionField = findViewById(R.id.mood_emotion_field)
        socialField = findViewById(R.id.mood_social_field)
        reasonField = findViewById(R.id.mood_reason_field)
        locationField = findViewById(R.id.mood_location_field)

        var emotionPosition: Int = 0
        var socialPosition: Int = 0

        // Emotional state spinner stuff

        val emotionalStates = arrayOf("\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")

        // TODO: For some reason some colors crash the app lol maybe find out why later (currently none of these do though)
        val colors = arrayOf(Color.GREEN, Color.parseColor("#33FFF4"), Color.YELLOW, Color.parseColor("#FF6D66"), Color.LTGRAY, Color.parseColor("#FE9DFF"))
        val emotionField = findViewById<Spinner>(R.id.mood_emotion_field)

        if (emotionField != null) {
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, emotionalStates)
            emotionField.adapter = arrayAdapter
            emotionPosition = arrayAdapter.getPosition(mood.getEmotion())

            emotionField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    emotionalStates[position]
                    val color = colors[position]
                    findViewById<View>(android.R.id.content).setBackgroundColor(color)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Social spinner stuff

        val socialSituations = arrayOf("None", "Alone", "With one other person", "With two to several people", "With a group")
        if (socialField != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, socialSituations)
            socialField.adapter = arrayAdapter
            socialPosition = arrayAdapter.getPosition(mood.getSocial())

            socialField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    socialSituations[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        emotionField.setSelection(emotionPosition)
        socialField.setSelection(socialPosition)
        dateField.text = mood.getDateString()
        reasonField.text = mood.getReasonText()

        // confirm button
        confirmButton.setOnClickListener {
            if (!verifyMood()) {
                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mood.setEmotion(emotionField.selectedItem.toString())
            mood.setSocial(socialField.selectedItem.toString())
            mood.setReasonText(reasonField.text.toString())

            val returnIntent = Intent()
            returnIntent.putExtra(INTENT_MOOD_RESULT, mood)

            if (flag == "edit") {
                val pos =intent.getIntExtra(HistoryView.INTENT_EDIT_POS, -1)
                returnIntent.putExtra(INTENT_POS_RESULT, pos)
            }

            setResult(RESULT_OK, returnIntent)

            presenter.confirmMood()
        }
    }

    override fun backtoHistory() {
        finish()
    }

    private fun verifyMood(): Boolean {
//        if (emotionField.selectedItem.toString().isEmpty())
//            return false
        if (reasonField.text.length > 20)
            return false
        if (reasonField.text.split(" ").size > 3)
            return false

        return true
    }

    companion object {
        const val INTENT_MOOD_RESULT = "mood_result"
        const val INTENT_POS_RESULT = "edit_pos"
    }

//    override fun gotoMap() {
//        val intent = Intent(this, MapView::class.java)
//        startActivity(intent)
//    }
}

