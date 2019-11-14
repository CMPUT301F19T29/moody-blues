package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.MoodWrapper
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

// TODO: Put in everything but dashboard
open class DbManager {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Shortcut methods
     */
    private fun getFF(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    private fun getFFMoods(username: String): CollectionReference {
        return getFF().collection(PATH_USERS)
                .document(username).collection(PATH_MOODS)
    }

    private fun getFFRequests(username:String): CollectionReference {
        return getFF().collection(PATH_USERS)
            .document(username).collection(PATH_REQUESTS)
    }

    // This is based on the following source:
    // https://firebase.google.com/docs/auth/android/firebaseui
    /**
     * Sign the user into Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    open suspend fun signIn(email: String, password: String): Boolean {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password)
                    .await()
            authResult != null && authResult.user != null
        } catch (e: Exception) {
            false // TODO: list exceptions
        }
    }

    /**
     * Create an account for the user in Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
    open suspend fun createUser(email: String, password: String, username: String): Boolean {
        val user = User(email, username)

        return try{
            val authResult = auth.createUserWithEmailAndPassword(email, password)
                    .await()
            if (authResult == null || authResult.user == null)
                return false
//            sendEmailVerification()
            getFF().collection(PATH_USERS).document(username)
                    .set(user)
                    .await()
            true
        } catch (e: Exception) {
            false // TODO: list exceptions
        }
    }

    /**
     * Delete the currently logged in user's account and delete their data
     * from the database
     * @return The result of deleting the user's account
     */
    @Deprecated("use username key instead")
    open suspend fun deleteCurrentUser(): Boolean {
        val email = getUserEmail()
        email?: return false

        getFF().collection(PATH_USERS).document(email)
                .delete()
                .await()
        auth.currentUser
                ?.delete()
                ?.await()
        return true
    }

    /**
     * Send a verification email to the currently signed in user
     */
    fun sendEmailVerification(){
        auth.currentUser?.sendEmailVerification()
    }

    /**
     * Get the email of the currently logged in user
     * @return The email of the signed in user
     */
    private fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    /**
     * Get the User object representing the user with the given email
     * from the database
     * @param email The email identifying the user to retrieve
     * @return The user with the given email, if they existed in
     *  the database, else null
     */
    protected suspend fun getUser(username: String): User? {
        return getFF().collection(PATH_USERS)
                .document(username).get()
                .await()
                .toObject(User::class.java)
    }

    /**
     * Sign out the currently signed in user
     */
    open fun signOut(){
        auth.signOut()
    }

    /**
     * Get a single mood with the given id, owned by the user with
     * the given email
     * @param email The email identifying the user from which to
     *  retrieve the mood
     * @return The mood with the given id, owned by the given user
     */
    @Deprecated("Use username getters instead")
    suspend fun getMood(id: String, username: String): Mood {
        val snapshot = getFFMoods(username).document(id)
                .get()
                .await()
        return Mood(snapshot.toObject(MoodWrapper::class.java)!!)
    }

    /**
     * Get a hashmap mapping database ids to moods for all of the
     * moods belonging to the user with the specified email
     * @param email The email identifying the user from which to
     *  retrieve moods
     * @return A hashmap of ids and Moods belonging to the given user
     */
    open suspend fun fetchMoods (username: String?): HashMap<String, Mood> {
        val moodMap = HashMap<String, Mood>()
        username?: return moodMap

        val moodSnapshot = getFFMoods(username)
                .get()
                .await()

        for (doc in moodSnapshot)
            moodMap[doc.id] = Mood(doc.toObject(MoodWrapper::class.java))

        return moodMap
    }

    /**
     * Add an empty mood to the database (to get a new id),
     * then updates it with the actual mood belonging to the user with the
     * given email
     *
     * @param mood The mood to add to the database
     * @param email The email of the user who will own the mood
     * @return The id of the mood in the database
     */
    protected suspend fun addMood(mood: Mood, username: String): String {
        val doc = getFFMoods(username)
                .add(mood.wrap())
                .await()

        doc.update("id", doc.id).await()
        mood.id = doc.id
        return doc.id
    }

    /**
     * Delete the mood with the specified id, belonging to the user with
     * the given email
     * @param id The id of the mood to delete
     * @param email The email of the user who owns the mood
     * @return The result of deleting the mood from the database
     */
    protected suspend fun deleteMood(id: String, username: String): Void? {
        return getFFMoods(username).document(id)
                .delete()
                .await()
    }

    /**
     * Update the mood specified by documentId and owned by the user with the given
     * email, so it contains the data from the given mood
     * @param id The id of the mood in the database to replace with the given mood
     * @param mood The mood to replace the existing mood in the database
     * @param email The email of the user owning the mood to be updated
     * @return The result of replacing the mood in the database
     */
    protected suspend fun editMood(id: String, mood: Mood, username: String): Void? {
        return getFFMoods(username).document(id)
                .set(mood.wrap())
                .await()
    }

    open suspend fun addRequest(request: Request) {
        getFFRequests(request.from).document(request.to)
            .set(request)
            .await()

        getFFRequests(request.to).document(request.from)
            .set(request)
            .await()
    }

    protected suspend fun deleteRequest(request: Request) {
        getFFRequests(request.from).document(request.to)
            .delete()
            .await()

        getFFRequests(request.to).document(request.from)
            .delete()
            .await()
    }

    protected suspend fun fetchRequests(username: String?): ArrayList<Request> {
        val requests = ArrayList<Request>()
        username?: return requests

        getFFRequests(username)

        return requests
    }

    companion object {
        private const val PATH_USERS: String = "users"
        private const val PATH_MOODS: String = "moods"
        private const val PATH_REQUESTS: String = "requests"
    }
}
