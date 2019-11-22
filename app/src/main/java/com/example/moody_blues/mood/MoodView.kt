package com.example.moody_blues.mood

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.history.HistoryView
import com.example.moody_blues.history.HistoryView.Companion.INTENT_MOOD
import com.example.moody_blues.history.HistoryView.Companion.INTENT_PURPOSE_ADD
import com.example.moody_blues.history.HistoryView.Companion.INTENT_PURPOSE_EDIT
import com.example.moody_blues.models.Mood
import com.squareup.picasso.Picasso
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import java.io.File
import java.io.FileOutputStream


/**
 * The view for the mood activity
 */
class MoodView : AppCompatActivity(), MoodContract.View {
    private val THUMBSIZE: Int = 150
    override lateinit var presenter: MoodContract.Presenter

    private lateinit var confirmButton: Button
    private lateinit var dateField: TextView
    private lateinit var emotionField: Spinner
    private lateinit var socialField: Spinner
    private lateinit var reasonField: TextView
    private lateinit var locationField: Switch
//    private lateinit var locationData: TextView
    private lateinit var photoField: ImageView
//    private var photoBitmap: Bitmap? = null
    private lateinit var photoAddButton: Button
    private lateinit var photoUploadButton: Button
    private lateinit var photoDeleteButton: Button
    private var photoLocalPath: String? = null

    private lateinit var mood: Mood

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            if (photoLocalPath == null && savedInstanceState.getString("photoLocalPath") != null) {
                photoLocalPath = savedInstanceState.getString("photoLocalPath")!!
            }
        }

        setContentView(R.layout.mood_view)
        mood = this.intent.getParcelableExtra(INTENT_MOOD) as Mood
        title = this.intent.getStringExtra(HistoryView.INTENT_PURPOSE) as String

        // Pass the view to the presenter
        presenter = MoodPresenter(this)

        // Find Views
        confirmButton = findViewById(R.id.mood_save_button)
        dateField = findViewById(R.id.mood_date_field)
        emotionField = findViewById(R.id.mood_emotion_field)
        socialField = findViewById(R.id.mood_social_field)
        reasonField = findViewById(R.id.mood_reason_field)
        locationField = findViewById(R.id.mood_location_field)
//        locationData = findViewById(R.id.mood_location_data)
        photoField = findViewById(R.id.mood_photo_field)
        photoAddButton = findViewById(R.id.mood_photo_add_button)
        photoUploadButton = findViewById(R.id.mood_photo_upload_button)
        photoDeleteButton = findViewById(R.id.mood_photo_delete_button)

        if (mood.reasonImageThumbnail != null){
            MainScope().launch {
                var uri = AppManager.getImageUri(mood.reasonImageThumbnail)
                Picasso.get().load(uri).rotate(90F).into(photoField)
            }
        }

        // Emotional state spinner stuff

        // TODO: For some reason some colors crash the app lol maybe find out why later (currently none of these do though)

        emotionField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.EMOTION_STATES)

        emotionField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                presenter.onSelectEmotion(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        // Social spinner stuff

        socialField.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Mood.SOCIAL_REASONS)

        socialField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter.onSelectSocial(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        photoAddButton.setOnClickListener {
            var pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val photoFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg", storageDir)
                photoLocalPath = photoFile.absolutePath

                var photoURI = FileProvider.getUriForFile(this, "$packageName.provider", photoFile)
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(pictureIntent, REQUEST_PHOTO_ADD)
            }
        }

        photoUploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_PHOTO_UPLOAD)
        }

        photoDeleteButton.setOnClickListener {
            presenter.setPhoto(null, null)
        }

        emotionField.setSelection(mood.emotion)
        socialField.setSelection(mood.social)
        dateField.text = mood.getDateString()
        reasonField.text = mood.reasonText
//        locationData.text = mood.location
        locationField.isChecked = mood.showLocation
//        photoField.setImageBitmap(mood.getImage())

        // confirm button
        confirmButton.setOnClickListener {
            presenter.verifyMoodFields(reasonField.text.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (photoLocalPath != null)
            outState.putString("photoLocalPath", photoLocalPath)
        super.onSaveInstanceState(outState)
    }


    override fun changeBgColor(color: Int) {
        findViewById<View>(android.R.id.content).setBackgroundColor(color)
    }

    override fun preBacktoHistory() {
        presenter.setMoodFields(
                mood,
                emotionField.selectedItemPosition,
                socialField.selectedItemPosition,
                reasonField.text.toString(),
                locationField.isChecked
        )

        if (title == INTENT_PURPOSE_ADD)
            presenter.addMood(mood)
        else if (title == INTENT_PURPOSE_EDIT)
            presenter.editMood(mood)
    }

    /**
     * Transition back to the history activity
     */
    override fun backtoHistory() {
        setResult(RESULT_OK, Intent())
        finish()
    }

    /**
     * Confirm the mood matches the requirements
     * @return If all conditions are met
     */
    override fun showVerifyError() {
        Toast.makeText(applicationContext, "Invalid input", Toast.LENGTH_SHORT).show()
    }

    override fun changePhoto(thumbnail: Bitmap?, photo: File?) {
        // cancel any existing requests
        Picasso.get().cancelRequest(photoField)
        photoField.setImageBitmap(thumbnail)

        // Replace with new Uris when they are available
        if (thumbnail == null){
            val previousThumbnail = mood.reasonImageThumbnail
            mood.reasonImageThumbnail = null
            mood.reasonImageFull = null
        }
        else {
            var thumbnailFile = File(applicationContext.getDir("IMAGES", Context.MODE_PRIVATE), UUID.randomUUID().toString() + ".jpg")
            var outStream = FileOutputStream(thumbnailFile)
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()

            mood.reasonImageFull = AppManager.storeFile(photo)
            mood.reasonImageThumbnail =  AppManager.storeFile(thumbnailFile)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        if (requestCode == REQUEST_PHOTO_ADD) {
            var thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photoLocalPath), THUMBSIZE, THUMBSIZE)
            presenter.setPhoto(thumbnail, File(photoLocalPath))
        }
        else if (requestCode == REQUEST_PHOTO_UPLOAD) {
            try {
                // TODO: Check if bitmap is full image or just thumbnail
                val uri = data?.data

                if (uri != null) {
                    val stream = contentResolver.openInputStream(uri!!)
                    val bitmap = BitmapFactory.decodeStream(stream)
                    var thumbnail = ThumbnailUtils.extractThumbnail(bitmap, THUMBSIZE, THUMBSIZE)

                    var fullFile = File(applicationContext.getDir("IMAGES", Context.MODE_PRIVATE), UUID.randomUUID().toString() + ".jpg")
                    var outStream = FileOutputStream(fullFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                    outStream.flush()
                    outStream.close()

                    presenter.setPhoto(thumbnail, fullFile)
                }
            } catch (e: Exception) {
                return
            }
        }
    }

    companion object {
        const val INTENT_MOOD_RESULT = "mood_result"
        const val INTENT_POS_RESULT = "edit_pos"
        const val REQUEST_PHOTO_ADD = 1
        const val REQUEST_PHOTO_UPLOAD = 2
    }

//    override fun gotoMap() {
//        val intent = Intent(this, MapView::class.java)
//        startActivity(intent)
//    }
}

