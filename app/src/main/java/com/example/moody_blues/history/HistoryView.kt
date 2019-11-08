package com.example.moody_blues.history

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.example.moody_blues.mood.MoodView
import com.example.moody_blues.mood.MoodView.Companion.INTENT_MOOD_RESULT
import com.example.moody_blues.mood.MoodView.Companion.INTENT_POS_RESULT
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

        history_list_mood.adapter = MoodAdapter(presenter.fetchMoods(),
            { item: Mood, pos: Int -> presenter.editMood(pos) },
            { item: Mood, pos: Int -> presenter.deleteMood(item)
                history_list_mood.adapter!!.notifyDataSetChanged()
                true })
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

        if (requestCode == GET_EDITED_MOOD_CODE && resultCode == RESULT_OK) {
            val mood: Mood = data?.getSerializableExtra(INTENT_MOOD_RESULT) as Mood
            val pos: Int = data.getIntExtra(INTENT_POS_RESULT, -1)
            presenter.updateMood(mood, pos)
            history_list_mood.adapter!!.notifyDataSetChanged()
        }
    }

    override fun gotoMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(FLAG, "add")
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_MOOD_CODE)
    }

    override fun refreshMoods(moods: ArrayList<Mood>) {
        val moodAdapter = history_list_mood.adapter as MoodAdapter
        moodAdapter.refresh(moods)
    }

    override fun gotoEditMood(pos: Int) {
        val mood: Mood = AppManager.getMood(pos)
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(FLAG, "edit")
        intent.putExtra(INTENT_MOOD, mood)
        intent.putExtra(INTENT_EDIT_POS, pos)
        startActivityForResult(intent, GET_EDITED_MOOD_CODE)
    }

    companion object {
        const val FLAG = "flag"
        const val INTENT_MOOD = "mood"
        const val INTENT_EDIT_POS = "editPos"
        const val GET_MOOD_CODE = 1
        const val GET_EDITED_MOOD_CODE = 2
    }
}

