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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        title = "History"

        filterField = findViewById(R.id.filter_moods)

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
            { item: Mood, pos: Int -> presenter.editMood(item) },
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
        if (requestCode == GET_MOOD_CODE && resultCode == RESULT_OK) {
            val filters = arrayOf("❌ No filter", "\uD83D\uDE0E Happy", "\uD83D\uDE20 Upset", "\uD83D\uDE06 Excited", "\uD83D\uDE24 Agitated", "\uD83D\uDE10 Bored", "\uD83E\uDD14 Uncertain")
            val mood: Mood = data?.getParcelableExtra(INTENT_MOOD_RESULT) as Mood
            presenter.addMood(mood)
            if(filterField.getSelectedItemPosition() != 0) {
                presenter.refreshMoods(filters[filterField.getSelectedItemPosition()])
            } else {
                presenter.refreshMoods()
            }
        }

        if (requestCode == GET_EDITED_MOOD_CODE && resultCode == RESULT_OK) {
            val mood: Mood = data?.getParcelableExtra(INTENT_MOOD_RESULT) as Mood
            val pos: Int = data.getIntExtra(INTENT_POS_RESULT, -1)
            presenter.updateMood(mood)
            history_list_mood.adapter!!.notifyDataSetChanged()
        }
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
     * @param id The id of the mood the edit or view
     */
    override fun gotoEditMood(id: String) {
        val mood: Mood? = AppManager.getMood(id)
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_PURPOSE, INTENT_PURPOSE_EDIT)
        intent.putExtra(INTENT_MOOD, mood)
        intent.putExtra(INTENT_EDIT_ID, id)
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

