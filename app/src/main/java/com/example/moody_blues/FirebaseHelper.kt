package com.example.moody_blues

import com.example.moody_blues.models.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseHelper {
    fun getFF(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    open suspend fun getUser(email: String) : User? {
        val user = getFF().collection(PATH_EMAILS).document(email)
            .get()
            .await()
            .toObject(User::class.java)

        return user
    }

    val PATH_USERS: String = "users"
    val PATH_MOODS: String = "moods"
    //        private const val PATH_REQUESTS: String = "requests"
    val PATH_EMAILS: String = "emails"
    val PATH_FROM: String = "from"
    val PATH_TO: String = "to"
}


