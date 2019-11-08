package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult

object AppManager : DbManager(){ // todo: inherit from DbManager
    private var userMoods: HashMap<String, Mood> = HashMap<String, Mood>()
    private var user: User? = null

    /**
     * Refresh the cached list of user moods by retrieving all moods for
     * the currently signed in user from the database
     * @return true if the cached moods were refreshed, false if they were not
     *  (Such as if a user is not currently signed in)
     */
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

    /**
     * Sign the user into Firebase so they can access the database, and also
     * create a cache of their moods from the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    override suspend fun signIn(email: String, password: String): AuthResult? {
        var authResult = super.signIn(email, password)
        return if (authResult == null || authResult.user == null){
            null
        }
        else{
            this.user = getUser(email)
            refreshMoods()
            authResult
        }
    }

    /**
     * Create an account for the user in Firebase so they can access the database,
     *  and also create a cache of their moods from the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
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

    /**
     * Get the username for the currently signed in user
     * @return The username for the signed in user
     */
    fun getUsername(): String?{
        return if (this.user == null){
            null
        }
        else{
            this.user!!.username
        }
    }

    /**
     * Delete signed in user's account and data, and remove their
     * data from local cache
     * @return The result of deleting their account from the database
     */
    override suspend fun deleteCurrentUser(): Void? {
        this.user = null
        this.userMoods.clear()
        return super.deleteCurrentUser()
    }

    /**
     * Sign out the currently signed in user, and clear their locally cached
     * data
     */
    override fun signOut() {
        this.user = null
        this.userMoods.clear()
        super.signOut()
    }

    /**
     * Get the user moods for the signed in user, filtered by emotion
     * @param emotion The emotion the filter for
     * @return The Map of all of the signed in user's moods with the
     *  specified emotion, where Mood ids are mapped to Moods
     */
    fun getFilteredUserMoods(emotion: String): Map<String, Mood> {
        return this.userMoods.filter { entry-> entry.value.getEmotionString() == emotion }
    }

    /**
     * Get the mood with the given id, belonging to the signed in user
     * @param id The id of the mood to return
     * @return The mood with the specified id, belonging to the signed in user
     */
    fun getMood(id: String) : Mood? {
        return this.userMoods[id]
    }

    /**
     * Get the moods owned by the signed in user
     * @return A map of the user's moods, where database id maps to the mood
     */
    fun getMoods(): HashMap<String, Mood> {
        return userMoods
    }

    /**
     * Add the given mood to the user's list of moods in the database
     * @param mood The mood to add to the database
     * @return The result of adding the mood to the database
     */
    suspend fun addMood(mood: Mood): String? {
        return if (this.user == null){
            null
        }
        else{
            var id = addMood(mood, this.user!!.id)
            mood.id = id
            this.userMoods[id] = mood
            id
        }
    }

    /**
     * Delete the mood with the specified id, belonging to the signed
     * in user
     * @param id The id of the mood to delete
     * @return The result of deleting the mood from the database
     */
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

    /**
     * Update the mood specified by documentId and owned by the signed in user, so
     * it contains the data from the given mood
     * @param id The id of the mood in the database to replace with the given mood
     * @param mood The mood to replace the existing mood in the database
     * @return The result of replacing the mood in the database
     */
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