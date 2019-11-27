package com.example.moody_blues.feed

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.map.MapView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.mood.MoodAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
            {mood: Mood, _: Int ->
                var imageView = ImageView(this)
                if (mood.reasonImageFull != null){
                    MainScope().launch {
                        var (uri, rotation) = AppManager.getImageUri(mood.username, mood.reasonImageFull!!)
                        if (uri != null){
                            Picasso.get().load(uri).rotate(rotation).into(imageView)
                        }
                        else{
                            imageView.setImageResource(android.R.color.transparent)
                        }
                    }
                }
                var builder = Dialog(this)
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
                builder.window?.setBackgroundDrawable(
                        ColorDrawable(Color.TRANSPARENT))
                builder.setOnDismissListener{
                    Picasso.get().cancelRequest(imageView)
                    imageView.setImageResource(android.R.color.transparent)
                }

                imageView.setOnClickListener{
                    builder.dismiss()
                }
                builder.addContentView(imageView, RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT))
                builder.show()
            },
            { _: Mood, _: Int -> true })

        feed_list_mood.layoutManager = LinearLayoutManager(this)

        mapButton.setOnClickListener {
            presenter.gotoMap()
        }
    }

    override fun gotoMap() {
        val intent = Intent(this, MapView::class.java)
        intent.putExtra("mode", 2)
        startActivity(intent)
    }
}

