package com.example.moody_blues.Data

class MoodRepo() {

    private val moods = ArrayList<Mood>()

    init {
        moods.add(Mood("moody", "im moody"))
    }
    fun getMoods() {
        return 
    }

    fun addMood(newMood: Mood) {
        moods.add(newMood)
    }

}
