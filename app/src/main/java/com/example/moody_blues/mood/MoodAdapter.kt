package com.example.moody_blues.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood

class MoodAdapter(private val moods: ArrayList<Mood>) : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = moods[position].getDate()
        holder.emotion.text = moods[position].getEmotion()
        holder.social.text = moods[position].getSocial()
        holder.reason.text = moods[position].getReasonText()
        holder.image.setImageResource(R.drawable.moody_blues_icon_background)
    }

    override fun getItemCount() = moods.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.row_mood_date)
        val emotion: TextView = itemView.findViewById(R.id.row_mood_emotion)
        val social: TextView = itemView.findViewById(R.id.row_mood_social)
        val reason: TextView = itemView.findViewById(R.id.row_mood_reason_text)
        val image: ImageView = itemView.findViewById(R.id.row_mood_image)
    }
}