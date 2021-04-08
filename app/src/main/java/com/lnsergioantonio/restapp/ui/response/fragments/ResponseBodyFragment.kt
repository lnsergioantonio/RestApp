package com.lnsergioantonio.restapp.ui.response.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.ext.newIntent
import kotlinx.android.synthetic.main.fragment_response_body.*

private const val ARG_BODY = "body"
private const val EMPTY_BODY = "Empty body"

class ResponseBodyFragment : Fragment() {
    private var body: String? = EMPTY_BODY

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
        loadExtras()
        return inflater.inflate(R.layout.fragment_response_body, container, false)
    }

    private fun loadExtras() {
        arguments?.let { bundle ->
            body = bundle.getString(ARG_BODY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        labelBody.text = if (body.isNullOrEmpty()) EMPTY_BODY else body
    }

    companion object {
        @JvmStatic
        fun newInstance(body: String?) =
                ResponseBodyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_BODY, body)
                    }
                }
    }
}