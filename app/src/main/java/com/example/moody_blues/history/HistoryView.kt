package com.example.moody_blues.history

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import kotlinx.coroutines.GlobalScope

/**
 * The view for the history activity
 */
class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var filterField: Spinner
    private lateinit var add: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        title = "History"

        add =  findViewById(R.id.history_add_button)
        filterField = findViewById(R.id.filter_moods)
        filterField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.EMOTION_FILTERS)
        filterField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter.refreshMoods(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

        // Do stuff with the presenter
        add.setOnClickListener {
            presenter.onAddMood()
        }

        history_list_mood.adapter = MoodAdapter(presenter.fetchMoods(0),
            { item: Mood, _: Int -> presenter.onEditMood(item) },
            { item: Mood, _: Int -> presenter.deleteMood(item)
                history_list_mood.adapter!!.notifyDataSetChanged()
                true })
        history_list_mood.layoutManager = LinearLayoutManager(this)
    }

    override fun getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        else {
            getLocationResult()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0)
            getLocationResult()
    }

    /**
     * Create a new mood when location is obtained
     */
    private fun getLocationResult() {
        fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location : Location? ->
                    presenter.createMood(location)
                }
    }

    /**
     * Callback function after a user returns from the mood activity
     * @param requestCode A code for creating a new mood or editing an existing one
     * @param resultCode A code for success
     * @param data The intent passed by the mood activity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.refreshMoods(filterField.getSelectedItemPosition())
    }

    /**
     * Open the mood activity for a mood
     * @param mood The mood the edit or view
     */
    override fun gotoMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_PURPOSE, INTENT_PURPOSE_ADD)
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_MOOD_CODE)
    }

    /**
     * Update the list of displayed moods
     * @param moods The new list of moods
     */
    override fun refreshMoods(moods: ArrayList<Mood>) {
        val moodAdapter = history_list_mood.adapter as MoodAdapter
        moodAdapter.refresh(moods)
    }

    /**
     * Open the mood activity for a mood
     * @param mood The mood to edit or view
     */
    override fun gotoEditMood(mood: Mood) {
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_PURPOSE, INTENT_PURPOSE_EDIT)
        intent.putExtra(INTENT_MOOD, mood)
        startActivityForResult(intent, GET_EDITED_MOOD_CODE)
    }

    companion object {
        const val INTENT_PURPOSE = "purpose"
        const val INTENT_PURPOSE_EDIT = "Edit Mood"
        const val INTENT_PURPOSE_ADD = "New Mood"
        const val INTENT_MOOD = "mood"
        const val INTENT_EDIT_ID = "edit id"
        const val GET_MOOD_CODE = 1
        const val GET_EDITED_MOOD_CODE = 2
    }
}

