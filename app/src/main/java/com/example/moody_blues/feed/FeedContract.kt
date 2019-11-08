package com.example.moody_blues.feed
import android.location.Location
import com.example.moody_blues.BasePresenter
import com.example.moody_blues.BaseView
import com.example.moody_blues.models.Mood

interface FeedContract {
    interface View : BaseView<Presenter> {
        fun refreshMoods(moods: ArrayList<Mood>)
    }

    interface Presenter : BasePresenter {
        fun fetchMoods(emotion: String): ArrayList<Mood>
        fun fetchMoods(): ArrayList<Mood>
        fun refreshMoods(emotion: String)
        fun refreshMoods()
    }
}
