package com.example.moody_blues.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.example.moody_blues.mood.MoodView
import com.example.moody_blues.mood.MoodView.Companion.INTENT_MOOD_RESULT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.history_view.*

class HistoryView : AppCompatActivity(), HistoryContract.View {
    override lateinit var presenter: HistoryContract.Presenter

    private val moods: ArrayList<Mood> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view)
        title = "History"

        // Pass the view to the presenter
        presenter = HistoryPresenter(this)

        // Do stuff with the presenter
        val add: FloatingActionButton = findViewById(R.id.history_add_button)

        add.setOnClickListener {
            presenter.createNewMood()
        }

//        val list: RecyclerView = findViewById(R.id.history_list_mood)
        history_list_mood.adapter = MoodAdapter(moods)
        history_list_mood.layoutManager = LinearLayoutManager(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("\n\n\n\nonActivityResult reached \n\n\n\n")
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val mood: Mood = data?.getSerializableExtra(INTENT_MOOD_RESULT) as Mood
            println(moods)
//            addMood(mood)
//            println(moods)
        }
    }

//    fun addMood(mood: Mood) {
//        moods.add(mood)
//        history_list_mood.adapter!!.notifyDataSetChanged()
//    }

    override fun gotoMood(mood: Mood) {
        moods.add(mood)
        val intent = Intent(this, MoodView::class.java)
        intent.putExtra(INTENT_MOOD, mood)
        startActivity(intent)
        history_list_mood.adapter!!.notifyDataSetChanged()
    }

    companion object {
        const val INTENT_MOOD = "mood"
    }
}

