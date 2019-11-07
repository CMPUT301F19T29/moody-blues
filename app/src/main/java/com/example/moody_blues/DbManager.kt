package com.example.moody_blues

import android.content.ContentValues.TAG
import android.util.Log
import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

// TODO: Put in everything but dashboard
class DbManager() {
    private val db : FirebaseFirestore
    init{
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance()
    }

    suspend fun addUser(user: User): Void? {
            return db.collection("users").document(user.username)
                    .set(user)
                    .await()
    }

    suspend fun getUser(username: String, onSuccess: (User?) -> Void, onFailure: (Exception) -> Void): User? {
        val document = db.collection("users").document(username)
                .get()
                .await()
        return document.toObject(User::class.java)
    }

    suspend fun getCurrentUserId(){
        FirebaseUser user =
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
