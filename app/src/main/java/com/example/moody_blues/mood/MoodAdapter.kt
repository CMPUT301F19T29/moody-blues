package com.example.moody_blues.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood

class MoodAdapter(val moods: ArrayList<Mood>) : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = moods[position].date
        holder.time.text = moods[position].time
        holder.emotion.text = moods[position].emotion
        holder.social.text = moods[position].social
        holder.reason.text = moods[position].reason_text
    }

    override fun getItemCount() = moods.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.row_mood_date)
        val time: TextView = itemView.findViewById(R.id.row_mood_time)
        val emotion: TextView = itemView.findViewById(R.id.row_mood_emotion)
        val social: TextView = itemView.findViewById(R.id.row_mood_social)
        val reason: TextView = itemView.findViewById(R.id.row_mood_reason_text)
    }
}
