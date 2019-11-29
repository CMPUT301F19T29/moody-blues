package com.example.moody_blues

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.moody_blues.models.Mood
import com.example.moody_blues.models.MoodWrapper
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.media.ExifInterface
import androidx.core.net.toUri
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.nio.ByteBuffer


/**
 * Manages database and login
 */
open class DbManager {

    private lateinit var auth : FirebaseAuth
    private var isInit : Boolean = false
    fun init(app: FirebaseApp? = null){
        auth = if (app == null){
            FirebaseAuth.getInstance()
        }
        else{
            FirebaseAuth.getInstance(app)
        }
        isInit = true
    }

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

    private fun getFFRequestsTo(username:String): CollectionReference {
        return getFF().collection(PATH_USERS)
            .document(username).collection(PATH_TO)
    }

    private fun getFFRequestsFrom(username:String): CollectionReference {
        return getFF().collection(PATH_USERS)
            .document(username).collection(PATH_FROM)
    }

    private fun getFS(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    // This is based on the following source:
    // https://firebase.google.com/docs/auth/android/firebaseui
    /**
     * Sign the user into Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @return The result of attempting to sign the user in
     */
    open suspend fun signIn(email: String, password: String): String? {
        if (!isInit){
            init()
        }
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password)
                    .await()

            if (authResult == null || authResult.user == null)
                return null

            val user = getFF().collection(PATH_EMAILS).document(email)
                .get()
                .await()
                .toObject(User::class.java)

            return user!!.username

        } catch (e: Exception) {
            Log.e("signIn", e.toString())
            null // TODO: list exceptions
        }
    }

    /**
     * Create an account for the user in Firebase so they can access the database
     * @param email The email identifying the user attempting to sign in
     * @param password The password to use to attempt a sign in
     * @param username The username to display to other users
     * @return The result of creating the user's account
     */
    open suspend fun createUser(email: String, password: String, username: String): String? {
        if (!isInit){
            init()
        }

        val user = User(email, username)

        return try{
            var userDocument = getFF().collection(PATH_USERS).document(username)
            if (userDocument.get().await().exists()){
                Log.e("createUser", "Username already exists")
                return "Username Already Exists"
            }

            val authResult = auth.createUserWithEmailAndPassword(email, password)
                    .await()
            if (authResult == null || authResult.user == null)
                return "Failed to create user account"
//            sendEmailVerification()
            userDocument.set(user).await()
            getFF().collection(PATH_EMAILS).document(email)
                .set(user)
                .await()
            null
        } catch (e: Exception) {
            Log.e("createUser", e.toString())
            "Cannot create user.\nTry again but do it better next time."
        }
    }

    /**
     * Delete the currently logged in user's account and delete their data
     * from the database
     * @return The result of deleting the user's account
     */
    @Deprecated("use username key instead")
    open suspend fun deleteCurrentUser(): Boolean {
        if (isInit){
            // get email and username
            val email = getUserEmail()
            email?: return false
            val username = getFF().collection(PATH_EMAILS).document(email)
                    .get()
                    .await()
                    .toObject(User::class.java)?.username

            // delete database data
            getFF().collection(PATH_EMAILS).document(email)
                    .delete()
                    .await()
            if (username != null) {
                getFF().collection(PATH_USERS).document(username)
                        .delete()
                        .await()
                getFS().reference.child(username).delete()
            }

            // delete user account
            auth.currentUser
                    ?.delete()
                    ?.await()
            return true
        }
        else{
            return false
        }
    }

    /**
     * Delete a user from the database
     * @param username The username of the user
     * @param email The email of the user
     */
    open suspend fun deleteUser(username: String, email: String) {
        getFF().collection(PATH_USERS).document(username)
            .delete()
            .await()
        getFF().collection(PATH_EMAILS).document(email)
            .delete()
            .await()

        // Delete images from firebase storage
        getFS().reference.child(username).delete()
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
     * @param username The username identifying the user to retrieve
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
     * @param id The id identifying the user from which to
     *  retrieve the mood
     * @return The mood with the given id, owned by the given user
     */
    @Deprecated("Use username getters instead")
    suspend fun getMood(id: String, username: String): Mood {
        val snapshot = getFFMoods(username).document(id)
                .get()
                .await()
        return Mood(snapshot.toObject(MoodWrapper::class.java)!!, id, username)
    }

    /**
     * Get a hashmap mapping database ids to moods for all of the
     * moods belonging to the user with the specified email
     * @param username The username of the user
     * @return A hashmap of ids and Moods belonging to the given user
     */
    protected suspend fun fetchMoods (username: String?): HashMap<String, Mood> {
        val moodMap = HashMap<String, Mood>()
        username?: return moodMap

        try {
            val moodSnapshot = getFFMoods(username)
                .get()
                .await()

            for (doc in moodSnapshot)
                moodMap[doc.id] = Mood(doc.toObject(MoodWrapper::class.java), doc.id, username)
        } catch (e: Exception) {
            Log.e("fetchMoods", e.toString())
        }

        return moodMap
    }

    /**
     * Add an empty mood to the database (to get a new id),
     * then updates it with the actual mood belonging to the user with the
     * given email
     *
     * @param mood The mood to add to the database
     * @param username The username of the user who will own the mood
     * @return The id of the mood in the database
     */
    protected suspend fun addMood(mood: Mood, username: String): String {
        mood.id = (UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE).toString()

        getFFMoods(username).document(mood.id).set(mood.wrap()).await()

        return mood.id

//        val doc = getFFMoods(username)
//                .add(mood.wrap())
//                .await()
//
//        doc.update("id", doc.id).await()
//        mood.id = doc.id
//        return doc.id
    }

    /**
     * Delete the mood with the specified id, belonging to the user with
     * the given email
     * @param id The id of the mood to delete
     * @param username The username of the user who owns the mood
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
     * @param username The username of the user owning the mood to be updated
     * @return The result of replacing the mood in the database
     */
    protected suspend fun editMood(id: String, mood: Mood, username: String): Void? {
        return getFFMoods(username).document(id)
                .set(mood.wrap())
                .await()
    }

    /**
     * Add or update an existing request
     * @param request The request to add or update
     */
    open suspend fun setRequest(request: Request) {
        getFFRequestsTo(request.from).document(request.to)
            .set(request)
            .await()

        getFFRequestsFrom(request.to).document(request.from)
            .set(request)
            .await()
    }

    /**
     * Delete an existing request
     * @param request The request to delete
     */
    protected suspend fun deleteRequest(request: Request) {
        getFFRequestsTo(request.from).document(request.to)
            .delete()
            .await()

        getFFRequestsFrom(request.to).document(request.from)
            .delete()
            .await()
    }

    /**
     * Fetch requests for a user
     * @param username The username of the user
     * @return The list of requests for the user
     */
    protected suspend fun fetchRequests(username: String?): ArrayList<Request> {
        val requests = ArrayList<Request>()
        try {
            username?: return requests

            val requestToSnapshot = getFFRequestsTo(username)
                .get()
                .await()

            for (doc in requestToSnapshot)
                requests.add(doc.toObject(Request::class.java))

            val requestFromSnapshot = getFFRequestsFrom(username)
                .get()
                .await()

            for (doc in requestFromSnapshot)
                requests.add(doc.toObject(Request::class.java))

        } catch (e: Exception) {
            Log.e("fetchRequests", e.toString())
        }

        return requests
    }

    /**
     * Store the image as a byte array in firebase storage, and return the url
     * from which the image can be retrieved
     * @param username The username of the user to which this image belongs
     * @param image The bitmap of the image to store in firebase
     * @return The url pointing to the image in cloud storage
     */
    @ExperimentalCoroutinesApi
    protected fun storeFile(username: String, image: Bitmap): String? {
        val filename = UUID.randomUUID().toString()
        val storageRef = getFS().reference.child(username).child(filename)

        MainScope().launch {
            // convert image to byte array
            val byteCount = image.byteCount
            val bytes = ByteArray(byteCount)
            val buffer = ByteBuffer.allocate(byteCount)
            image.copyPixelsToBuffer(buffer)
            buffer.rewind()
            buffer.get(bytes)

            storageRef.putBytes(bytes).await()
        }

        return filename
    }

    /**
     * Store the image as in firebase storage, and return the url from which
     * the image can be retrieved
     * @param username The username of the user to which this image belongs
     * @param image The uri pointing to the image file in local storage
     * @return The url pointing to the image in cloud storage
     */
    @ExperimentalCoroutinesApi
    protected fun storeFile(username: String, image: File): String? {
        val filename = UUID.randomUUID().toString() + ".jpg"
        val storageRef = getFS().reference.child(username).child(filename)

        MainScope().launch {
            val ei = ExifInterface(image.path)
            val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            var rotation = 0F
            when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90F
                ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180F
                ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270F
            }
            val metadata = StorageMetadata.Builder().setCustomMetadata("rotation", rotation.toString()).build()
            storageRef.putFile(image.toUri(), metadata).await()
        }

        return filename
    }

    /**
     * Delete a file from storage
     * @param username The username of the owner
     * @param filename The filename of the file to delete
     */
    @ExperimentalCoroutinesApi
    protected fun deleteFile(username: String, filename: String) {
        val storageRef = getFS().reference.child(username).child(filename)

        MainScope().launch {
            val activeUploads = storageRef.activeUploadTasks
            for (activeUpload in activeUploads) {
                activeUpload.await()
            }
            storageRef.delete().await()
        }
    }
    /**
     * Get rotation and the uri to the image with the given file name
     * @param filename The name of the file in firestore
     * @return A pair where the first element is the Uri to the file, and
     *  the second is the rotation of the image in degrees
     */
    suspend fun getImageUri(username: String, filename: String): Pair<Uri?, Float> {
        val storageRef = getFS().reference.child(username).child(filename)

        val activeUploads = storageRef.activeUploadTasks
        for (activeUpload in activeUploads) {
            activeUpload.await()
        }

        var rotation = storageRef.metadata.await().getCustomMetadata("rotation")?.toFloat()
        if (rotation == null){
            rotation = 0F
        }

        return Pair(storageRef.downloadUrl.await(), rotation)
    }

    companion object {
        private const val PATH_USERS: String = "users"
        private const val PATH_MOODS: String = "moods"
//        private const val PATH_REQUESTS: String = "requests"
        private const val PATH_EMAILS: String = "emails"
        private const val PATH_FROM: String = "from"
        private const val PATH_TO: String = "to"
    }
}
