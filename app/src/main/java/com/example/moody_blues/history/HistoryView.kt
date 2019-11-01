package com.example.moody_blues.history

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

        // Do stuff with the presenter
        val add: FloatingActionButton = findViewById(R.id.history_add_button)

        add.setOnClickListener {
            presenter.createNewMood()
        }
    }

    override fun gotoMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_MOOD, mood)
        startActivity(intent)
    }

    companion object {
        const val INTENT_MOOD = "mood"
    }
}

