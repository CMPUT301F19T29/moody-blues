package com.example.moody_blues.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RequestAdapter(private var requests: ArrayList<Request>, private val pageNumber: Int) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = requests[position]

        if (pageNumber == 1) {
            holder.username.text = request.from

            holder.accept.setOnClickListener {
                acceptRequest(request)
            }
            holder.reject.setOnClickListener {
                rejectRequest(request)
            }
        }
        if (pageNumber == 2) {
            holder.accept.isVisible = false
            holder.reject.isVisible = false
            holder.cancel.isVisible = true

            holder.username.text = requests[position].to

            holder.cancel.setOnClickListener {
                cancelRequest(request)
            }
        }
    }

    override fun getItemCount() = requests.size

    private fun acceptRequest(request: Request) {
        MainScope().launch {
            requests = AppManager.acceptRequest(request)
            notifyDataSetChanged()
        }
    }

    private fun rejectRequest(request: Request) {
        MainScope().launch {
            requests = AppManager.rejectRequest(request)
            notifyDataSetChanged()
        }
    }

    private fun cancelRequest(request: Request) {
        MainScope().launch {
            requests = AppManager.cancelRequest(request)
            notifyDataSetChanged()
        }
    }

    fun refresh() {
        requests = AppManager.getRequestsFromSelf(true)
        notifyDataSetChanged()
    }

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