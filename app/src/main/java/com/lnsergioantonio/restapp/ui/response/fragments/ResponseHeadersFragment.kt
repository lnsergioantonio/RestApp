package com.lnsergioantonio.restapp.ui.response.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.R

private const val ARG_HEADERS = "headers"

class ResponseHeadersFragment : Fragment() {
    private var headers: Map<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            headers = it.getSerializable(ARG_HEADERS) as Map<String, String>?
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_response_headers, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(headers: HashMap<String, String>) =
                ResponseHeadersFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_HEADERS, headers)
                    }
                }
    }
}