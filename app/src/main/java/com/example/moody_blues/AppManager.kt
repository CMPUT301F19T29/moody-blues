package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult

object AppManager : DbManager(){ // todo: inherit from DbManager
    private var userMoods: HashMap<String, Mood> = HashMap<String, Mood>()
    private var user: User? = null

    suspend fun refreshMoods(): Boolean {
        return if (this.user == null){
            false
        }
        else{
            this.userMoods.clear()
            if (this.user!!.id != ""){
                userMoods = getMoods(this.user!!.id)
            }
            true
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult? {
        var authResult = super.signIn(email, password)
        return if (authResult == null || authResult.user == null){
            authResult
        }
        else{
            this.user = getUser(email)
            refreshMoods()
            authResult
        }
    }

    override suspend fun createUser(email: String, password: String, username: String): AuthResult? {
        var authResult = super.createUser(email, password, username)
        return if (authResult == null || authResult.user == null){
            authResult
        }
        else{
            this.user = getUser(email)
            refreshMoods()
            authResult
        }
    }

    fun getUsername(): String?{
        return if (this.user == null){
            null
        }
        else{
            this.user!!.username
        }
    }

    override suspend fun deleteCurrentUser(): Void? {
        this.user = null
        this.userMoods.clear()
        return super.deleteCurrentUser()
    }

    override fun signOut() {
        this.user = null
        this.userMoods.clear()
        super.signOut()
    }

    fun getFilteredUserMoods(emotion: String): Map<String, Mood> {
        return this.userMoods.filter { entry-> entry.value.getEmotion() == emotion }
    }

    fun getMood(id: String) : Mood? {
        return this.userMoods[id]
    }

    fun getMoods(): HashMap<String, Mood> {
        return userMoods
    }

    suspend fun addMood(mood: Mood): String? {
        return if (this.user == null){
            null
        }
        else{
            var id = addMood(mood, this.user!!.id)
            this.userMoods[id] = mood
            id
        }
    }

    suspend fun deleteMood(id: String): Boolean {
        return if (this.user == null){
            false
        }
        else{
            this.userMoods.remove(id)
            deleteMood(id, this.user!!.id)
            true
        }
    }

    suspend fun updateMood(id: String, mood: Mood): Boolean {
        return if (this.user == null){
            false
        }
        else{
            this.userMoods[id] = mood
            updateMood(id, mood, this.user!!.id)
            true
        }
    }
}