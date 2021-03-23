package com.lnsergioantonio.restapp.ui.response.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.R

private const val ARG_BODY = "body"

class ResponseBodyFragment : Fragment() {
    private var body: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            body = it.getString(ARG_BODY)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_response_body, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(body: String) =
                ResponseBodyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_BODY, body)
                    }
                }
    }
}