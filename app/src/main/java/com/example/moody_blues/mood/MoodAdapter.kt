package com.example.moody_blues.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood

/**
 * An adapter class for a mood row
 */
class MoodAdapter(private var moods: ArrayList<Mood>, private val clickListener: (Mood, Int) -> Unit, private val longListener: (Mood, Int) -> Boolean) : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = moods[position].getDateString()
        holder.emotion.text = moods[position].getEmotionString()
        holder.social.text = moods[position].getSocialString()
        holder.reason.text = moods[position].reason_text
        holder.username.text = AppManager.getUsername()
        holder.image.setImageResource(R.drawable.moody_blues_icon_background)
        holder.itemView.setBackgroundColor(moods[position].getColor())
        val item: Mood = moods[position]
        holder.itemView.setOnClickListener { clickListener(item, position) }
        holder.itemView.setOnLongClickListener { longListener(item, position) }
    }

    override fun getItemCount() = moods.size

    /**
     * Replace the list of moods
     * @param moods The new list of mooods
     */
    fun refresh(moods: ArrayList<Mood>) {
        this.moods = moods
        notifyDataSetChanged()
    }

    /**
     * A class representing a view holder for the mood adapter
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.row_mood_date)
        val emotion: TextView = itemView.findViewById(R.id.row_mood_emotion)
        val social: TextView = itemView.findViewById(R.id.row_mood_social)
        val reason: TextView = itemView.findViewById(R.id.row_mood_reason_text)
        val image: ImageView = itemView.findViewById(R.id.row_mood_image)
        val username: TextView = itemView.findViewById(R.id.row_mood_username)
    }
}
