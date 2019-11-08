package com.example.moody_blues.history

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.example.moody_blues.mood.MoodView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.moody_blues.mood.MoodView.Companion.INTENT_MOOD_RESULT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.history_view.*

class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        title = "History"

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

        // Do stuff with the presenter
        val add: FloatingActionButton = findViewById(R.id.history_add_button)

        add.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_DENIED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
            }
            else {
                getLocationResult()
            }
        }

//        // testing by initialising with fake moods
//        val fakeMood: Mood = Mood("2019-11-05", "1:22", "to test", null, "social", "confused")
//        moods.add(fakeMood)

        history_list_mood.adapter = MoodAdapter(presenter.fetchMoods())
        history_list_mood.layoutManager = LinearLayoutManager(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0)
            getLocationResult()
    }

    private fun getLocationResult() {
        fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location : Location? ->
                    presenter.createMood(location)
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_MOOD_CODE && resultCode == RESULT_OK) {
            val mood: Mood = data?.getSerializableExtra(INTENT_MOOD_RESULT) as Mood
            presenter.addMood(mood)
        }
    }

    override fun gotoMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_MOOD_CODE)
    }

    override fun refreshMoods(moods: ArrayList<Mood>) {
        val moodAdapter = history_list_mood.adapter as MoodAdapter
        moodAdapter.refresh(moods)
    }

    companion object {
        const val INTENT_MOOD = "mood"
        const val GET_MOOD_CODE = 1
    }
}

