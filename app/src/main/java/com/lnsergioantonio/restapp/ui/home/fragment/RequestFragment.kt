package com.lnsergioantonio.restapp.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lnsergioantonio.restapp.App
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.di.RequestContainer
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import com.lnsergioantonio.restapp.ext.onItemSelectedChanged
import com.lnsergioantonio.restapp.ext.value
import com.lnsergioantonio.restapp.ui.home.fragment.dialog.*
import com.lnsergioantonio.restapp.ui.header.AddHeadersActivity
import com.lnsergioantonio.restapp.ui.header.HEADERS_LIST
import com.lnsergioantonio.restapp.ui.header.RESULT_HEADERS_LIST
import com.lnsergioantonio.restapp.ui.header.adapter.HeadersItem
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {
    private var headersItems: ArrayList<HeadersItem> = arrayListOf()
    private lateinit var viewModel: RequestViewModel
    private var numberCalls: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initObservers()
        initListeners()
        toggleProgress(false)
    }

    private fun toggleProgress(isShow:Boolean) {
        if (isShow){
            progressBar.visibility = View.VISIBLE
            labelProgress.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.INVISIBLE
            labelProgress.visibility = View.INVISIBLE
        }

    }

    private fun initListeners() {
        val methodRequestList = resources.getStringArray(R.array.method_request)

        spinnerMethod.onItemSelectedChanged { position ->
            viewModel.setMethod(methodRequestList[position])
        }

        buttonHeaders.setOnClickListener {
            startHeaderScreen()
        }

        buttonBody.setOnClickListener {
            val dialog = BodyDialog { body, bodyType ->
                viewModel.setBody(body, bodyType)
            }
            dialog.show(parentFragmentManager)
        }

        buttonSend.setOnClickListener {
            toggleProgress(true)
            numberCalls = inputNumber.value.toIntOrNull() ?: 1
            numberCalls = if (numberCalls == 0) 1 else numberCalls
            viewModel.setRequestUrl(inputUrl.value, inputNumber.value)
            viewModel.sendRequest()
            initSubmitViews()
        }
    }

    private fun initSubmitViews() {
        buttonSend.isEnabled = false
        progressBar.max = numberCalls
        progressBar.progress = 0
    }

    private fun initViewModel() {
        val appContainer = (requireActivity().application as App).appContainer
        viewModel = RequestContainer(appContainer.sendRequestRepository).viewModel
    }

    private fun initObservers() {
        viewModel.responseState.observe(viewLifecycleOwner, this::onResponseStateChange)
        viewModel.requestState.observe(viewLifecycleOwner, this::onRequestStateChange)
    }

    private fun onRequestStateChange(request: RequestViewModel.Request?) {
        request?.let {
            headersItems = it.getHeaders()
        }
    }

    private fun onResponseStateChange(resultList: List<State<ResponseEntity>>?) {
        resultList?.let { noNullResult ->
            noNullResult.count {
                when (it) {
                    is State.Failure -> {
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG)
                        true
                    }
                    is State.Progress -> false
                    is State.Success -> true
                }
            }.let { responseCompleted ->
                progressBar.progress = responseCompleted
                buttonSend.isEnabled = responseCompleted == numberCalls
                labelProgress.text = String.format("%s / %s", responseCompleted, numberCalls)
            }
        }
    }

    private fun startHeaderScreen() {
        val intent = Intent(requireActivity(), AddHeadersActivity::class.java)

        intent.putParcelableArrayListExtra(HEADERS_LIST, headersItems)
        startActivityForResult(intent, RESULT_HEADERS_LIST)
        requireActivity().overridePendingTransition(R.transition.push_down_in, R.transition.push_down_out)
    }

    fun updateHeadersList(headersList: List<HeadersItem>) {
        viewModel.clearHeaders()
        headersList.forEach {
            viewModel.setHeader(it.key, it.value)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RequestFragment()
    }
}