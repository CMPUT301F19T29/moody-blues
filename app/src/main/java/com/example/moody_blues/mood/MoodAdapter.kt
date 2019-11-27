package com.example.moody_blues.mood

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Mood
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * An adapter class for a mood row
 */
class MoodAdapter(private var moods: ArrayList<Mood>, private val clickListener: (Mood, Int) -> Unit, private val longListener: (Mood, Int) -> Boolean) : RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_mood, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var mood = moods[position]
        holder.date.text = mood.getDateString()
        holder.emotion.text = mood.getEmotionString()
        holder.social.text = mood.getSocialString()
        holder.reason.text = mood.reasonText
        holder.username.text = mood.username
        holder.itemView.setBackgroundColor(mood.getColor())
        val item: Mood = mood
        holder.itemView.setOnClickListener { clickListener(item, position) }
        holder.itemView.setOnLongClickListener { longListener(item, position) }

        if (mood.reasonImageThumbnail != null) {
            MainScope().launch {
                val (uri, rotation) = AppManager.getImageUri(mood.username, mood.reasonImageThumbnail!!)
                if (uri != null){
                    Picasso.get().load(uri).rotate(rotation).into(holder.image)
                }
                else{
                    holder.image.setImageResource(R.drawable.moody_blues_icon_background)
                }
            }
        }
        else{
            holder.image.setImageResource(R.drawable.moody_blues_icon_background)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        Picasso.get().cancelRequest(holder.image)
        super.onViewRecycled(holder)
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
