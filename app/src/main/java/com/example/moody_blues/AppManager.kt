package com.example.moody_blues

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult
import com.squareup.picasso.Picasso
import java.io.File

object AppManager : DbManager(){
    private var userMoods: HashMap<String, Mood> = HashMap()
    private var userRequests: ArrayList<Request> = ArrayList()
    private var userFeed: ArrayList<Mood> = ArrayList()
    private var user: User? = null

    /**
     * Signs the user in so they can access the database, and also
     * create a cache of their moods from the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    override suspend fun signIn(email: String, password: String): String? {
        val username = super.signIn(email, password)
        username?: return null

        this.user = getUser(username)
        this.fetchMoods()
        this.fetchRequests()
        this.fetchFeed()
        return username
    }

    override suspend fun deleteUser(username: String, email: String) {
        super.deleteUser(username, email)
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

    suspend fun fetchMoods(): HashMap<String, Mood> {
        val moods = super.fetchMoods(user!!.username)
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
    @Deprecated("use username getters instead")
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
        val id = super.addMood(mood, this.user!!.username)
        this.userMoods[id] = mood
    }

    /**
     * Delete the mood with the specified id, belonging to the signed
     * in user
     * @param id The id of the mood to delete
     */
    suspend fun deleteMood(id: String) {
        super.deleteMood(id, this.user!!.username)
        this.userMoods.remove(id)
    }

    /**
     * Update the mood specified by id
     * @param mood The mood to replace the existing mood in the database
     */
    suspend fun editMood(mood: Mood) {
        super.editMood(mood.id, mood, this.user!!.username)
        this.userMoods[mood.id] = mood
    }

    suspend fun addRequest(to: String) {
        super.setRequest(Request(user!!.username, to))
    }

    suspend fun cancelRequest(to: String) {
        super.deleteRequest(Request(user!!.username, to))
    }

    suspend fun rejectRequest(from: String) {
        super.deleteRequest(Request(from, user!!.username))
    }

    suspend fun acceptRequest(from: String) {
        super.setRequest(Request(from, user!!.username, true))
    }

    suspend fun fetchRequests(): ArrayList<Request> {
        this.userRequests = super.fetchRequests(user!!.username)
        return this.userRequests
    }

    fun getRequests(): ArrayList<Request> {
        return this.userRequests
    }

    fun getRequestsFromOthers(): ArrayList<Request> {
        val requests = ArrayList<Request>()

        for (r in userRequests)
            if (r.to == user!!.username)
                requests.add(r)

        return requests
    }

    fun getRequestsFromSelf(): ArrayList<Request> {
        val requests = ArrayList<Request>()

        for (r in userRequests)
            if (r.from == user!!.username)
                requests.add(r)

        return requests
    }

    private suspend fun fetchMostRecentMood(username: String): Mood? {
        val moodMap = super.fetchMoods(username)
        val moodArray = ArrayList(moodMap.values.sortedByDescending { mood -> mood.date })
        return if (moodArray.size == 0) null else moodArray[0]
    }

    suspend fun fetchFeed(): ArrayList<Mood> {
        // MUST FETCH REQUESTS FIRST

        val feed = ArrayList<Mood>()
        val requests = getRequestsFromSelf()

        for (r in requests) {
//            if (!r.accepted)
//                continue
            val m = fetchMostRecentMood(r.to)
            if (m != null) feed.add(m)
        }

        this.userFeed = feed
        return feed
    }

    fun storeImage(thumbnail: File?, full: File?): Pair<String?, String?> {
        // TODO Convert image to the smaller version
        val fullFilename = this.user?.username?.let {
            if (full == null) {
                null
            }
            else {
                super.storeImage(it, full, false)
            }
        }
        val thumbnailFilename = this.user?.username?.let {
            if (thumbnail == null) {
                null
            }
            else{
                super.storeImage(it, thumbnail, true)
            }
        }
        return Pair(thumbnailFilename, fullFilename)
    }

    suspend fun getImageUri(filename: String?): Uri? {
        return this.user?.username?.let {
            if (filename == null) {
                null
            }
            else {
                super.getImageUri(it, filename)
            }
        }
    }
}