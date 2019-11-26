package com.example.moody_blues.feed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.R
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import kotlinx.android.synthetic.main.feed_view.*

class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)
        title = "Feed"

        // Pass the view to the presenter
        presenter = FeedPresenter(this)

        // Do stuff with the presenter
        feed_list_mood.adapter = MoodAdapter(presenter.getFeed(),
            {_: Mood, _: Int ->  },
            { _: Mood, _: Int -> true })
        feed_list_mood.layoutManager = LinearLayoutManager(this)
    }

    override fun gotoMap() {
        val intent = Intent(this, MapView::class.java)
        startActivity(intent)
    }
}

