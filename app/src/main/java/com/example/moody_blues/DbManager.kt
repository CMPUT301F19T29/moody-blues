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
    private var userMoods : ArrayList<Mood> = ArrayList()

    // This is based on the following source:
    // https://firebase.google.com/docs/auth/android/firebaseui
    open suspend fun signIn(email: String, password: String): AuthResult? {
        var authResult = auth.signInWithEmailAndPassword(email,password).await()
        return authResult
    }

    open suspend fun createUser(email: String, password: String, username: String): AuthResult? {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        if (authResult.user != null){
//            sendEmailVerification()
            val db = FirebaseFirestore.getInstance()
            var user = User(username)
            db.collection("users").document(email)
                    .set(user)
        }
        return authResult
    }

    open suspend fun deleteCurrentUser(): Void? {
        return auth.currentUser?.delete()?.await()
    }

    fun sendEmailVerification(){
        auth.currentUser?.sendEmailVerification()
    }

    fun getSignedInUserEmail(): String? {
        return auth.currentUser?.email
    }

    open fun signOut(){
        auth.signOut()
    }

    protected suspend fun addMood(mood: Mood, email: String): String {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("users")
                .document(email).collection("moods")
                .add(mood)
                .await()
        return documentReference.id
    }

    suspend fun getFilteredMood(emotion: String, email: String): HashMap<String, Mood> {
        var moods = FirebaseFirestore.getInstance().collection("users").document(email)
                .collection("moods").whereEqualTo("emotion", emotion)
                .get().await()
        var moodMap = HashMap<String, Mood>()
        for (doc in moods){
            moodMap[doc.id] = doc.toObject(Mood::class.java)
        }
        return moodMap
    }

    suspend fun getMood(id: String, email: String): Mood? {
        val db = FirebaseFirestore.getInstance()
        return db.collection("users").document(email)
                .collection("moods").document(id)
                .get().await().toObject(Mood::class.java)
    }

    suspend fun getMoods(email: String): HashMap<String, Mood> {
        val db = FirebaseFirestore.getInstance()

        val moodSnapshot = db.collection("users")
                .document(email).collection("moods")
                .get()
                .await()
        var moodMap = HashMap<String, Mood>()
        for (doc in moodSnapshot){
            moodMap[doc.id] = doc.toObject(Mood::class.java)
        }
        return moodMap
    }

    protected suspend fun deleteMood(documentId: String, email: String): Void? {
        val db = FirebaseFirestore.getInstance()
        return db.collection("users").document(email)
                .collection("moods").document(documentId)
                .delete()
                .await()
    }

    protected suspend fun updateMood(documentId: String, mood: Mood, email: String): Void? {
        val db = FirebaseFirestore.getInstance()
        return db.collection("users").document(email)
                .collection("moods").document(documentId)
                .set(mood)
                .await()
    }
}
