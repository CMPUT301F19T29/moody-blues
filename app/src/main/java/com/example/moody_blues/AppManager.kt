package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult

object AppManager : DbManager(){
    private var userMoods: HashMap<String, Mood> = HashMap()
    private var user: User? = null

    /**
     * Signs the user in so they can access the database, and also
     * create a cache of their moods from the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    override suspend fun signIn(email: String, password: String): Boolean {
        if (!super.signIn(email, password))
            return false

        this.user = getUser(email)
        this.fetchMoods(email)
        return true
    }

    /**
     * Create an account for the user
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
    override suspend fun createUser(email: String, password: String, username: String): Boolean {
        return super.createUser(email, password, username) // probably not needed
    }

    override suspend fun fetchMoods(email: String?): HashMap<String, Mood> {
        val moods = super.fetchMoods(email)
        this.userMoods = moods
        return moods
    }

    /**
     * Get the username for the currently signed in user
     * @return The username for the signed in user
     */
    fun getUsername(): String? {
        return this.user?.username
    }

    /**
     * Delete signed in user's account and data, and remove their
     * data from local cache
     * @return The result of deleting their account from the database
     */
    override suspend fun deleteCurrentUser(): Boolean {
        if (!super.deleteCurrentUser())
            return false

        this.user = null
        this.userMoods.clear()
        return true
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
    @Deprecated("use getUserMoods() instead")
    fun getFilteredUserMoods(emotion: String): Map<String, Mood> {
        return this.userMoods.filter { entry -> entry.value.getEmotionString() == emotion }
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
    @Deprecated("use getUserMoods() instead")
    fun getMoods(): HashMap<String, Mood> {
        return this.userMoods
    }

    /**
     * Get the user moods for the signed in user, possibly filtered by emotion
     * @param emotion The emotion the filter for, or NULL for no filter
     * @return The hashmap of requested moods
     */
    fun getUserMoods(emotion: Int?): HashMap<String, Mood> {
        return if (emotion == null)
            this.userMoods
        else
            this.userMoods.filter { entry -> entry.value.emotion == emotion } as HashMap
    }

    fun getOrderedUserMoods(emotion: Int?): ArrayList<Mood> {
        return ArrayList(this.getUserMoods(emotion).values.sortedByDescending { mood -> mood.date })
    }

    /**
     * Add the given mood to the user's list of moods in the database
     * @param mood The mood to add to the database
     */
    suspend fun addMood(mood: Mood) {
        val id = super.addMood(mood, this.user!!.id)
        this.userMoods[id] = mood
    }

    /**
     * Delete the mood with the specified id, belonging to the signed
     * in user
     * @param id The id of the mood to delete
     */
    suspend fun deleteMood(id: String) {
        super.deleteMood(id, this.user!!.id)
        this.userMoods.remove(id)
    }

    /**
     * Update the mood specified by id
     * @param mood The mood to replace the existing mood in the database
     */
    suspend fun editMood(mood: Mood) {
        super.editMood(mood.id, mood, this.user!!.id)
        this.userMoods[mood.id] = mood
    }
}