package com.example.moody_blues

import com.example.moody_blues.models.Mood

object AppManager { // todo: inherit from dbManager
    private var userMoods: ArrayList<Mood> = ArrayList()
    private var username: String = ""

    fun updateUserMoods() {
        this.userMoods.clear()
    }

    fun getUserMoods(username: String = this.username): ArrayList<Mood> {
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
}