package com.lnsergioantonio.restapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.fragment.dialog.*
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {
    private val REQUEST_FRAGMENT = "REQUEST_FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonHeaders.setOnClickListener {
            startHeaderScreen()
        }

        buttonBody.setOnClickListener {
            val dialog = BodyDialog { body, bodyType ->
                Log.e(REQUEST_FRAGMENT, "$body $bodyType")
                //viewModel save body and type
            }
            dialog.show(parentFragmentManager)
        }
    }

    private fun startHeaderScreen() {
        val headersItems: ArrayList<HeadersItem> = arrayListOf()
        val intent = Intent(requireActivity(), AddHeadersActivity::class.java)

        intent.putParcelableArrayListExtra(HEADERS_LIST, headersItems)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.transition.push_down_in, R.transition.push_down_out)
    }

    fun updateHeadersList(headersList: List<HeadersItem>) {
        // viewModel save headers
    }

    companion object {
        @JvmStatic
        fun newInstance() = RequestFragment()
    }
}