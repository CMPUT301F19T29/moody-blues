package com.example.moody_blues.models

import android.media.Image

class Mood(
   var date: String,
   var time: String,
   var reason_text: String,
   var reason_image: Image,
   var social: Int,
   var emotion: Int) {
}
