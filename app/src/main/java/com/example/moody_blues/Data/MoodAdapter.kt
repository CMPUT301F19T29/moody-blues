package com.example.moody_blues.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MoodAdapter(moods: List<Mood>)
    : BaseAdapter() {

    var moods: List<Mood> = moods
        set(moods) {
            field = moods
            notifyDataSetChanged()
        }

    override fun getCount() = moods.size
    override fun getItem(i: Int) = moods[i]
    override fun getItemId(i: Int) = i.toLong()

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val mood = getItem(i)

        val view = view ?: LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        val moodView: TextView = view.findViewById(R.id.mood_text)
        val descriptionView: TextView = view.findViewById(R.id.description_text)
        moodView.text = mood.mood
        descriptionView.text = mood.description

        return view
    }
}
