package com.example.moody_blues.mood

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.history.HistoryView.Companion.INTENT_MOOD
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
    private lateinit var locationField: Switch
    private lateinit var locationData: TextView

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
        locationData = findViewById(R.id.mood_location_data)


        // Emotional state spinner stuff

        // TODO: For some reason some colors crash the app lol maybe find out why later (currently none of these do though)

        if (emotionField != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.EMOTION_STATES)
            emotionField.adapter = arrayAdapter

            emotionField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Mood.EMOTION_STATES[position]
                    val color = Mood.EMOTION_COLORS[position]
                    findViewById<View>(android.R.id.content).setBackgroundColor(color)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Social spinner stuff

        if (socialField != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.SOCIAL_REASONS)
            socialField.adapter = arrayAdapter

            socialField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Mood.SOCIAL_REASONS[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        emotionField.setSelection(mood.emotion?: 0)
        socialField.setSelection(mood.social?: 0)
        dateField.text = mood.getDateString()
        reasonField.text = mood.getReasonText()
        locationData.text = mood.location
        locationField.setChecked(mood.showLocation)

        // confirm button
        confirmButton.setOnClickListener {
            if (!verifyMood()) {
                Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mood.emotion = emotionField.selectedItemPosition
            mood.social = socialField.selectedItemPosition
            mood.setReasonText(reasonField.text.toString())
            mood.showLocation = locationField.isChecked

            val returnIntent = Intent()
            returnIntent.putExtra(INTENT_MOOD_RESULT, mood)

            if (flag == "edit") {
                val pos =intent.getIntExtra(HistoryView.INTENT_EDIT_ID, -1)
                returnIntent.putExtra(INTENT_POS_RESULT, pos)
            }

            setResult(RESULT_OK, returnIntent)

            presenter.confirmMood(mood)
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

