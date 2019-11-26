package com.example.moody_blues.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import kotlinx.android.synthetic.main.feed_view.*

class FeedView : AppCompatActivity(), FeedContract.View {
    override lateinit var presenter: FeedContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)
        title = "Feed"

        // TODO fetch moods of followed users
//        val list = getFollowedMoods()
        val list = ArrayList<Mood>()
        list.add(Mood("fakeID", "fakeUsername", null, reasonText = "fakeReason", showLocation = false))


        // Pass the view to the presenter
        presenter = FeedPresenter(this)

        // Do stuff with the presenter
        feed_list_mood.adapter = MoodAdapter(list,
            {_: Mood, _: Int ->  },
            { _: Mood, _: Int -> true })
        feed_list_mood.layoutManager = LinearLayoutManager(this)
    }

    override fun getFollowedMoods(): ArrayList<Mood> {
        // TODO fetch moods of followed users
        return ArrayList<Mood>()
    }
}

