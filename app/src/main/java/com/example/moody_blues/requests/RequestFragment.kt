package com.example.moody_blues.requests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.example.moody_blues.models.Request
import com.example.moody_blues.models.User

/**
 * A placeholder fragment containing a simple view.
 */
class RequestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val pageNumber: Int  = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
        val root = inflater.inflate(R.layout.request_fragment, container, false)
        val requestList: RecyclerView = root.findViewById(R.id.request_list)
        requestList.layoutManager = LinearLayoutManager(context)

        if (pageNumber == 1) {  // incoming
            requestList.adapter = RequestAdapter(AppManager.getRequestsFromOthers(true), pageNumber)
        }
        else if (pageNumber == 2) {  // outgoing
            requestList.adapter = RequestAdapter(AppManager.getRequestsFromSelf(true), pageNumber)
        }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): RequestFragment {
            return RequestFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}