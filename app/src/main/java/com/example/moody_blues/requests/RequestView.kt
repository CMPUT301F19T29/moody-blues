package com.example.moody_blues.requests

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moody_blues.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RequestView : AppCompatActivity(), RequestContract.View {
    override lateinit var presenter: RequestContract.Presenter
    private lateinit var addRequestButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_view)

        addRequestButton = findViewById(R.id.request_add_button)

        // Pass the view to the presenter
        presenter = RequestPresenter(this)

        // Do stuff with the presenter
        addRequestButton.setOnClickListener {
            val builder: AlertDialog.Builder? = AlertDialog.Builder(this)
            builder
                ?.setView(R.layout.request_dialog)
                ?.setTitle("Follow a user")
                ?.setPositiveButton("Ok",  DialogInterface.OnClickListener { dialog, id ->
                    val field = (dialog as Dialog).findViewById<EditText>(R.id.request_dialog_field)
                    presenter.requestFollow(field.text.toString())
                })
                ?.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    (dialog as Dialog).cancel()
                })
            builder?.show()
        }

    }

    /**
     * Open a popup to select a user to follow
     */
    override fun gotoFollowUser() {
        // opens a popup to enter a user to follow
    }
}

