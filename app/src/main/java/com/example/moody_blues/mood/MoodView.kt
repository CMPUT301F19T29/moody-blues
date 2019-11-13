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
import com.example.moody_blues.history.HistoryView.Companion.INTENT_PURPOSE_ADD
import com.example.moody_blues.history.HistoryView.Companion.INTENT_PURPOSE_EDIT
import com.example.moody_blues.models.Mood

/**
 * The view for the mood activity
 */
class MoodView : AppCompatActivity(), MoodContract.View {
    override lateinit var presenter: MoodContract.Presenter

    private lateinit var confirmButton: Button
    private lateinit var dateField: TextView
    private lateinit var emotionField: Spinner
    private lateinit var socialField: Spinner
    private lateinit var reasonField: TextView
    private lateinit var locationField: Switch
    private lateinit var locationData: TextView

    private lateinit var mood: Mood

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mood_view)
        mood = this.intent.getParcelableExtra(INTENT_MOOD) as Mood
        title = this.intent.getStringExtra(HistoryView.INTENT_PURPOSE) as String

        confirmButton = findViewById(R.id.mood_save_button)
        dateField = findViewById(R.id.mood_date_field)
        emotionField = findViewById(R.id.mood_emotion_field)
        socialField = findViewById(R.id.mood_social_field)
        reasonField = findViewById(R.id.mood_reason_field)
        locationField = findViewById(R.id.mood_location_field)
        locationData = findViewById(R.id.mood_location_data)


        // Emotional state spinner stuff

        // TODO: For some reason some colors crash the app lol maybe find out why later (currently none of these do though)

        emotionField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.EMOTION_STATES)

        emotionField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                presenter.onSelectEmotion(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        // Social spinner stuff

        socialField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.SOCIAL_REASONS)

        socialField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter.onSelectSocial(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }


        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        emotionField.setSelection(mood.emotion)
        socialField.setSelection(mood.social)
        dateField.text = mood.getDateString()
        reasonField.text = mood.reason_text
//        locationData.text = mood.location
        locationField.isChecked = mood.showLocation

        // confirm button
        confirmButton.setOnClickListener {
            presenter.verifyMoodFields(reasonField.text.toString())
        }
    }

    override fun changeBgColor(color: Int) {
        findViewById<View>(android.R.id.content).setBackgroundColor(color)
    }

    override fun preBacktoHistory() {
        presenter.setMoodFields(
                mood,
                emotionField.selectedItemPosition,
                socialField.selectedItemPosition,
                reasonField.text.toString(),
                locationField.isChecked
        )

        if (title == INTENT_PURPOSE_ADD)
            presenter.addMood(mood)
        else if (title == INTENT_PURPOSE_EDIT)
            presenter.editMood(mood)
    }

    /**
     * Transition back to the history activity
     */
    override fun backtoHistory() {
        setResult(RESULT_OK, Intent())
        finish()
    }

    /**
     * Confirm the mood matches the requirements
     * @return If all conditions are met
     */
    override fun showVerifyError() {
        Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
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

