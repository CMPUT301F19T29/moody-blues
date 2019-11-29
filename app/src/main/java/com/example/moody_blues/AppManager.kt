package com.example.moody_blues

import android.net.Uri
import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User
import com.firebase.ui.auth.data.model.User.getUser
import java.io.File

/**
 * Manages local data and database
 */
object AppManager {
    private var userMoods: HashMap<String, Mood> = HashMap()
    private var userRequests: ArrayList<Request> = ArrayList()
    private var userFeed: ArrayList<Mood> = ArrayList()
    private var user: User? = null
//    lateinit var db: DbManager
    private var dbUp = false
    var db: DbManager = DbManager()

    fun init(newDb: DbManager = DbManager()) {
        db = newDb
        dbUp = true
    }

    /**
     * Signs the user in so they can access the database, and also
     * create a cache of their moods from the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    suspend fun signIn(email: String, password: String): String? {
//        if (!dbUp) {
//            init()
//        }
        val username = db.signIn(email, password)
        username?: return null

        this.user = db.getUser(username)
        return username
    }

    /**
     * Delete a user
     * @param username The username of the user
     * @param email the email of the user
     */
    suspend fun deleteUser(username: String, email: String) {
        db.deleteUser(username, email)
    }

    /**
     * Create an account for the user
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
    suspend fun createUser(email: String, password: String, username: String): String? {
//        if (!dbUp) {
//            init()
//        }
        return db.createUser(email, password, username) // probably not needed
    }

    /**
     * Fetches the user's moods from the database
     * @return A hashmap of the moods. ID is the key
     */
    suspend fun fetchMoods(): HashMap<String, Mood> {
        val moods = db.fetchMoods(user!!.username)
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
    suspend fun deleteCurrentUser(): Boolean {
        if (!db.deleteCurrentUser())
            return false

        this.user = null
        this.userMoods.clear()
        return true
    }

    /**
     * Sign out the currently signed in user, and clear their locally cached data
     */
    fun signOut() {
        this.user = null
        this.userMoods.clear()
        db.signOut()
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
    fun getUserMood(id: String) : Mood? {
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
     * @return The hashmap of requested moods. ID is the key
     */
    fun getUserMoods(emotion: Int?): HashMap<String, Mood> {
        return if (emotion == null)
            this.userMoods
        else
            this.userMoods.filter { entry -> entry.value.emotion == emotion } as HashMap
    }

    /**
     * Get the user's moods, ordered from most recent
     * @param emotion An optional emotion ID to filter for
     */
    fun getOrderedUserMoods(emotion: Int?): ArrayList<Mood> {
        return ArrayList(this.getUserMoods(emotion).values.sortedByDescending { mood -> mood.date })
    }

    /**
     * Add the given mood to the user's list of moods in the database
     * @param mood The mood to add to the database
     */
    suspend fun addMood(mood: Mood) {
        val id = db.addMood(mood, this.user!!.username)
        this.userMoods[id] = mood
    }

    /**
     * Delete the mood with the specified id, belonging to the signed
     * in user
     * @param id The id of the mood to delete
     */
    suspend fun deleteMood(id: String) {
        val mood = getUserMood(id)
        if (mood != null) {
            mood.reasonImageThumbnail?.let { db.deleteFile(this.user!!.username, it) }
            mood.reasonImageFull?.let { db.deleteFile(this.user!!.username, it) }
        }
        db.deleteMood(id, this.user!!.username)
        this.userMoods.remove(id)
    }

    /**
     * Update the mood specified by id
     * @param mood The mood to replace the existing mood in the database
     */
    suspend fun editMood(mood: Mood) {
        db.editMood(mood.id, mood, this.user!!.username)
        this.userMoods[mood.id] = mood
    }

    /**
     * Add a request to another user
     * @param to The username of the other user
     */
    suspend fun addRequest(to: String) {
        val request = Request(user!!.username, to)
        db.setRequest(request)
        this.userRequests.add(request)
    }

    /**
     * Cancel a sent request to another user
     * @param request The request to cancel
     * @return The new requests for the current user (from self)
     */
    suspend fun cancelRequest(request: Request) : ArrayList<Request> {
        db.deleteRequest(request)
        this.userRequests.remove(request)
        return this.getRequestsFromSelf(true)
    }

    /**
     * Reject another user's request
     * @param request The request to reject
     * @return The new requests for the current user (from others)
     */
    suspend fun rejectRequest(request: Request) : ArrayList<Request> {
        db.deleteRequest(request)
        this.userRequests.remove(request)
        return this.getRequestsFromOthers(true)
    }

    /**
     * Accept another user's request
     * @param request the request to accept
     * @return The new requests for the current user (from others)
     */
    suspend fun acceptRequest(request: Request) : ArrayList<Request> {
        val newRequest = Request(request.from, request.to, true)
        db.setRequest(newRequest)
        this.userRequests.remove(request)
        this.userRequests.add(newRequest)
        return this.getRequestsFromOthers(true)
    }

    /**
     * Fetch the requests from the database
     * @return All requests for the current user
     */
    suspend fun fetchRequests(): ArrayList<Request> {
        this.userRequests = db.fetchRequests(user!!.username)
        return this.userRequests
    }

    /**
     * Get all requests for the current user
     * @return The list of requests for the current user
     */
    fun getRequests(): ArrayList<Request> {
        return this.userRequests
    }

    /**
     * Get a list of requests from others
     * @param getPending Whether or not to only get pending requests
     * @return The list of requests from others
     */
    fun getRequestsFromOthers(getPending: Boolean): ArrayList<Request> {
        val requests = ArrayList<Request>()

        for (r in userRequests) {
            if (getPending && r.accepted)
                continue
            if (r.to == user!!.username)
                requests.add(r)
        }

        return requests
    }

    /**
     * Get a list of requests from self
     * @param getPending Whether or not to only get pending requests
     * @return The list of requests from self
     */
    fun getRequestsFromSelf(getPending: Boolean): ArrayList<Request> {
        val requests = ArrayList<Request>()

        for (r in userRequests) {
            if (getPending && r.accepted)
                continue
            if (r.from == user!!.username)
                requests.add(r)
        }

        return requests
    }

    /**
     * Get the most recent mood from the database
     * @param username The username of the mood owner
     * @return The most recent mood, if possible
     */
    private suspend fun fetchMostRecentMood(username: String): Mood? {
        val moodMap = db.fetchMoods(username)
        val moodArray = ArrayList(moodMap.values.sortedByDescending { mood -> mood.date })
        return if (moodArray.size == 0) null else moodArray[0]
    }

    /**
     * Fetch the feed for the current user
     * @return The list of moods for the feed
     */
    suspend fun fetchFeed(): ArrayList<Mood> {
        // MUST FETCH REQUESTS FIRST

        val feed = ArrayList<Mood>()
        val requests = getRequestsFromSelf(false)

        for (r in requests) {
            if (!r.accepted)
                continue
            val m = fetchMostRecentMood(r.to)
            if (m != null) feed.add(m)
        }

        this.userFeed = feed
        return feed
    }

    /**
     * Get the feed from memory
     * @return The list of moods for the feed
     */
    fun getFeed(): ArrayList<Mood> {
        return this.userFeed
    }

    /**
     * Store the given file in firebase storage and return the filename
     * to use to retrieve or delete the file
     * @param file The file to store
     * @return The filename identifying this file in the database
     */
    fun storeFile(file: File?): String?{
        val filename = this.user?.username?.let {
            if (file == null) {
                null
            }
            else{
                db.storeFile(it, file)
            }
        }
        return filename
    }

    /**
     * Delete an image from the storage
     * @param filename The image name
     */
    fun deleteImage(filename: String?){
        if (filename != null) {
            this.user?.username?.let { db.deleteFile(it, filename) }
        }
    }

    /**
     * Get rotation and the uri to the image with the given file name
     * @param filename The name of the file in firestore
     * @return A pair where the first element is the Uri to the file, and
     *  the second is the rotation of the image in degrees
     */
    suspend fun getImageUri(filename: String?): Pair<Uri?, Float> {
        var result = this.user?.username?.let {
            if (filename == null) {
                null
            }
            else {
                db.getImageUri(it, filename)
            }
        }
        if (result == null){
            result = Pair(null, 0F)
        }
        return result
    }
}
