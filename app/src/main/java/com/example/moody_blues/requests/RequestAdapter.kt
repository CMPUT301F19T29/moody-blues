package com.example.moody_blues.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.R
import com.example.moody_blues.models.User
import com.google.android.material.snackbar.Snackbar

class RequestAdapter(private var requests: ArrayList<User>, private val pageNumber: Int) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = requests[position].username
        if (pageNumber == 1) {
            holder.accept.setOnClickListener {
                Snackbar.make(it, "accepting this request!", Snackbar.LENGTH_LONG).show()
//                acceptRequest()
            }
            holder.reject.setOnClickListener {
                Snackbar.make(it, "rejecting this request!", Snackbar.LENGTH_LONG).show()
//                rejectRequest()
            }
        }
        if (pageNumber == 2) {
            holder.accept.isVisible = false
            holder.reject.isVisible = false
            holder.cancel.isVisible = true

            holder.cancel.setOnClickListener {
                Snackbar.make(it, "cancelling this request!", Snackbar.LENGTH_LONG).show()
//                cancelRequest()
            }
        }
    }

    override fun getItemCount() = requests.size

    /**
     * A class representing a view holder for the Request adapter
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.row_request_username)
        val accept: Button = itemView.findViewById(R.id.row_request_accept_button)
        val reject: Button = itemView.findViewById(R.id.row_request_reject_button)
        val cancel: Button = itemView.findViewById(R.id.row_request_cancel_button)
    }
}