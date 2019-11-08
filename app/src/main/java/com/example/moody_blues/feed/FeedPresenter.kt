package com.example.moody_blues.feed

import com.example.moody_blues.AppManager
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import kotlinx.android.synthetic.main.history_view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FeedPresenter(val feedView: FeedContract.View) : FeedContract.Presenter {

    // Constructor cannot contain any code
    // Init gets called after constructor
    // Called in same order as body
    // Can use val/vars from [primary
    init {
        // Links the presenter to the view
        feedView.presenter = this
    }

    override fun start() {
    }

    override fun fetchMoods(emotion: String): ArrayList<Mood> {
        return ArrayList<Mood>()
//        var moods : Collection<Mood> = AppManager.getFilteredUserMoods(emotion).values
//        moods.sortedByDescending { mood -> mood.date }
//        return ArrayList<Mood>(moods)

    }

    override fun fetchMoods(): ArrayList<Mood> {
        return ArrayList<Mood>()
//        var moods : Collection<Mood> = AppManager.getMoods().values
//        moods.sortedByDescending { mood -> mood.date }
//        return ArrayList<Mood>(moods)
    }

    override fun refreshMoods(emotion: String) {
//        MainScope().launch{
//            var moods = AppManager.getFilteredUserMoods(emotion).values.sortedByDescending { mood -> mood.date }
//            feedView.refreshMoods(ArrayList<Mood>(moods))
//        }
    }

    override fun refreshMoods() {
//        MainScope().launch{
//            var moods = AppManager.getMoods().values.sortedByDescending { mood -> mood.date }
//            feedView.refreshMoods(ArrayList<Mood>(moods))
//        }
    }
}
