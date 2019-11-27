package com.example.moody_blues.feed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.R
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.feed_view.*

/**
 * Toolkit-specific logic for the feed activity
 */
class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter

    private lateinit var mapButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)
        title = "Feed"

        mapButton = findViewById(R.id.feed_goto_map_button)

        // Pass the view to the presenter
        presenter = FeedPresenter(this)

        // Do stuff with the presenter
        feed_list_mood.adapter = MoodAdapter(presenter.getFeed(),
            {_: Mood, _: Int ->  },
            { _: Mood, _: Int -> true })

        feed_list_mood.layoutManager = LinearLayoutManager(this)

        mapButton.setOnClickListener {
            presenter.gotoMap()
        }
    }

    /**
     * Transition to the map activity
     */
    override fun gotoMap() {
        val intent = Intent(this, MapView::class.java)
        intent.putExtra("mode", 2)
        startActivity(intent)
    }
}

