package com.example.moody_blues

import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

// TODO: Put in everything but dashboard
class DbManager() {
    private val db : FirebaseFirestore
    private val auth : FirebaseAuth
    init{
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    // This is based on the following source:
    // https://firebase.google.com/docs/auth/android/firebaseui
    suspend fun signIn(username: String, password: String){

    }

    fun signOut(){
//        auth.getInstance.signOut()
    }

    suspend fun getSignedInUsername(){
//        return auth.currentUser.displayName
    }

//    suspend fun addUser(user: User): Void? {
//            return db.collection("users").document(user.username)
//                    .set(user)
//                    .await()
//    }

    suspend fun getUser(username: String, onSuccess: (User?) -> Void, onFailure: (Exception) -> Void): User? {
        val document = db.collection("users").document(username)
                .get()
                .await()
        return document.toObject(User::class.java)
    }

    suspend fun removeUser(username: String, onSuccess: () -> Void, onFailure: (Exception) -> Void): Void? {
        return db.collection("users").document(username)
                .delete()
                .await()
    }

    suspend fun addMood(username: String, mood: Mood): String {
        val documentReference = db.collection("users").document(username).collection("moods")
                .add(mood)
                .await()
        return documentReference.id
    }

    suspend fun getMoods(username: String): QuerySnapshot? {
        return db.collection("users").document(username).collection("moods")
                .get()
                .await()
    }

    suspend fun removeMood(username: String, documentId: String): Void? {
        return db.collection("users").document(documentId)
                .delete()
                .await()
    }

}
