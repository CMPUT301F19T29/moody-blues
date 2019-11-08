package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.google.firebase.auth.AuthResult

object AppManager : DbManager(){ // todo: inherit from DbManager
    private var userMoods: HashMap<String, Mood> = HashMap<String, Mood>()
    private var userEmail: String = ""

    suspend fun updateUserMoods() {
        this.userMoods.clear()
        if (this.userEmail != ""){
            userMoods = getMoods(userEmail)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult? {
        var authResult = super.signIn(email, password)
        return if (authResult != null && authResult.user != null){
            updateUserMoods()
            authResult
        }
        else{
            authResult
        }
    }

    override suspend fun createUser(email: String, password: String, username: String): AuthResult? {
        var authResult = super.createUser(email, password, username)
        return if (authResult != null && authResult.user != null){
            this.userEmail = authResult.user!!.email.toString()
            updateUserMoods()
            authResult
        }
        else{
            authResult
        }
    }

    override suspend fun deleteCurrentUser(): Void? {
        this.userEmail = ""
        this.userMoods.clear()
        return super.deleteCurrentUser()
    }

    override fun signOut() {
        this.userEmail = ""
        this.userMoods.clear()
        super.signOut()
    }

    suspend fun getFilteredUserMoods(emotion: String): Map<String, Mood> {
        return this.userMoods.filter { entry-> entry.value.getEmotion() == emotion }
    }

    fun getMood(id: String) : Mood? {
        return this.userMoods[id]
    }

    fun getMoods(): HashMap<String, Mood> {
        return userMoods
    }

    suspend fun addMood(mood: Mood): String {
        var id = addMood(mood,this.userEmail)
        this.userMoods[id] = mood
        return id
    }

    suspend fun deleteMood(id: String){
        this.userMoods.remove(id)
        deleteMood(id, this.userEmail)
    }

    suspend fun updateMood(id: String, mood: Mood){
        this.userMoods[id] = mood
        updateMood(id, mood, this.userEmail)
    }
}