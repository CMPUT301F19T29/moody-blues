package com.example.moody_blues

import android.util.Log
import com.example.moody_blues.models.Mood

object AppManager { // todo: inherit from DbManager
    private var userMoods: ArrayList<Mood> = ArrayList()
    private var username: String = ""


    fun updateUserMoods() {
        this.userMoods.clear()
    }

    fun getFilteredUserMoods(emotion: String, username: String = this.username): ArrayList<Mood> {
        val moods: ArrayList<Mood> = getUserMoods(username)
        return moods.filter { mood -> mood.getEmotion() == emotion } as ArrayList<Mood>
    }

    fun getUserMoods(username: String = this.username): ArrayList<Mood> {
        userMoods.sortByDescending { it.getDate() }
        return this.userMoods
    }

    fun addUserMood(mood: Mood, username: String = this.username) {
        this.userMoods.add(mood)
    }

    fun updateMood(mood: Mood, pos: Int) {
        this.userMoods[pos] = mood
    }

    fun getUsername(): String {
        return this.username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    // mock get mood method placeholder
    fun getMood(pos: Int): Mood {
        return userMoods[pos]
    }
}