package com.example.moody_blues.feed

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.history_view.*

class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var filterField: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        title = "History"

        filterField = findViewById(R.id.filter_moods)

        // Pass the view to the presenter
        presenter = FeedPresenter(this)


        val filters = arrayOf("❌ No filter", "\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filters)
        filterField.adapter = arrayAdapter
        filterField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(filters[position] != "❌ No filter") {
                    presenter.refreshMoods(filters[position])
                } else {
                    presenter.refreshMoods()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
        // Do stuff with the presenter
    }
        
    override fun refreshMoods(moods: ArrayList<Mood>) {
        val moodAdapter = history_list_mood.adapter as MoodAdapter
        moodAdapter.refresh(moods)
    }
}

