package com.lnsergioantonio.restapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.App
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.di.RequestContainer
import com.lnsergioantonio.restapp.ext.onItemSelectedChanged
import com.lnsergioantonio.restapp.ext.value
import com.lnsergioantonio.restapp.ui.fragment.dialog.*
import com.lnsergioantonio.restapp.ui.header.AddHeadersActivity
import com.lnsergioantonio.restapp.ui.header.HEADERS_LIST
import com.lnsergioantonio.restapp.ui.header.RESULT_HEADERS_LIST
import com.lnsergioantonio.restapp.ui.header.adapter.HeadersItem
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {
    private lateinit var viewModel: RequestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val methodRequestList = resources.getStringArray(R.array.method_request)

        initViewModel()
        buttonHeaders.setOnClickListener {
            startHeaderScreen()
        }

        buttonBody.setOnClickListener {
            val dialog = BodyDialog { body, bodyType ->
                viewModel.setBody(body, bodyType)
            }
            dialog.show(parentFragmentManager)
        }

        spinnerMethod.onItemSelectedChanged { position ->
            viewModel.setMethod(methodRequestList[position])
        }

        buttonSend.setOnClickListener {
            viewModel.setRequestUrl(inputUrl.value, inputNumber.value)
            viewModel.sendRequest()
        }
    }

    private fun initViewModel() {
        val appContainer = (requireActivity().application as App).appContainer
        viewModel = RequestContainer(appContainer.sendRequestRepository).viewModel
    }

    private fun startHeaderScreen() {
        val headersItems: ArrayList<HeadersItem> = arrayListOf()
        val intent = Intent(requireActivity(), AddHeadersActivity::class.java)

        intent.putParcelableArrayListExtra(HEADERS_LIST, headersItems)
        startActivityForResult(intent, RESULT_HEADERS_LIST)
        requireActivity().overridePendingTransition(R.transition.push_down_in, R.transition.push_down_out)
    }

    fun updateHeadersList(headersList: List<HeadersItem>) {
        headersList.forEach {
            viewModel.setHeader(it.key, it.value)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RequestFragment()
    }
}