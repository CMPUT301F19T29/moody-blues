package com.example.moody_blues

import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// TODO: Put in everything but dashboard
open class DbManager {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    // This is based on the following source:
    // https://firebase.google.com/docs/auth/android/firebaseui
    /**
     * Sign the user into Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    open suspend fun signIn(email: String, password: String): AuthResult? {
        var authResult = auth.signInWithEmailAndPassword(email,password).await()
        return authResult
    }

    /**
     * Create an account for the user in Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
    open suspend fun createUser(email: String, password: String, username: String): AuthResult? {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        if (authResult.user != null){
//            sendEmailVerification()
            val db = FirebaseFirestore.getInstance()
            var user = User(email, username)
            db.collection("users").document(email)
                    .set(user).await()
        }
        return authResult
    }

    /**
     * Delete the currently logged in user's account and delete their data
     * from the database
     * @return The result of deleting the user's account
     */
    open suspend fun deleteCurrentUser(): Void? {
        val email = getUserEmail()
        return if (email == null) {
            null
        }
        else{
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(email)
                    .delete().await()
            auth.currentUser?.delete()?.await()
        }

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
    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    /**
     * Get the User object representing the user with the given email
     * from the database
     * @param email The email identifying the user to retreive
     * @return The user with the given email, if they existed in
     *  the database, else null
     */
    protected suspend fun getUser(email: String): User? {
        val db = FirebaseFirestore.getInstance()
        if (email == null){
            return null
        }
        else {
            return db.collection("users")
                    .document(email).get().await()
                    .toObject(User::class.java)
        }
    }

    /**
     * Sign out the currently signed in user
     */
    open fun signOut(){
        auth.signOut()
    }

    /**
     * Add a mood to the database, belonging to the user with the
     * given email
     * @param mood The mood to add to the database
     * @param email The email of the user who will own the mood
     * @return The id of the mood in the database
     */
    protected suspend fun addMood(mood: Mood, email: String): String {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("users")
                .document(email).collection("moods")
                .add(mood)
                .await()
        return documentReference.id
    }

    /**
     * Get a single mood with the given id, owned by the user with
     * the given email
     * @param email The email identifying the user from which to
     *  retrieve the mood
     * @return The mood with the given id, owned by the given user
     */
    suspend fun getMood(id: String, email: String): Mood? {
        val db = FirebaseFirestore.getInstance()
        var snapshot = db.collection("users").document(email)
                .collection("moods").document(id)
                .get().await()
        var mood = snapshot.toObject(Mood::class.java)!!
        mood.id = snapshot.id
        return mood
    }

    /**
     * Get a hashmap mapping database ids to moods for all of the
     * moods belonging to the user with the specified email
     * @param email The email identifying the user from which to
     *  retrieve moods
     * @return A hashmap of ids and Moods belonging to the given user
     */
    suspend fun getMoods(email: String): HashMap<String, Mood> {
        val db = FirebaseFirestore.getInstance()

        val moodSnapshot = db.collection("users")
                .document(email).collection("moods")
                .get()
                .await()
        var moodMap = HashMap<String, Mood>()
        for (doc in moodSnapshot){
            var mood = doc.toObject(Mood::class.java)
            mood.id = doc.id
            moodMap[doc.id] = mood
        }
        return moodMap
    }

    /**
     * Delete the mood with the specified id, belonging to the user with
     * the given email
     * @param documentId The id of the mood to delete
     * @param email The email of the user who owns the mood
     * @return The result of deleting the mood from the database
     */
    protected suspend fun deleteMood(documentId: String, email: String): Void? {
        val db = FirebaseFirestore.getInstance()
        return db.collection("users").document(email)
                .collection("moods").document(documentId)
                .delete()
                .await()
    }

    /**
     * Update the mood specified by documentId and owned by the user with the given
     * email, so it contains the data from the given mood
     * @param documentId The id of the mood in the database to replace with the given mood
     * @param mood The mood to replace the existing mood in the database
     * @param email The email of the user owning the mood to be updated
     * @return The result of replacing the mood in the database
     */
    protected suspend fun updateMood(documentId: String, mood: Mood, email: String): Void? {
        val db = FirebaseFirestore.getInstance()
        return db.collection("users").document(email)
                .collection("moods").document(documentId)
                .set(mood)
                .await()
    }
}
