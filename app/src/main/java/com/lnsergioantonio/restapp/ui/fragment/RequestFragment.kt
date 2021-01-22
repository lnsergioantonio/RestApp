package com.lnsergioantonio.restapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnsergioantonio.restapp.BuildConfig
import com.lnsergioantonio.restapp.R
import com.lnsergioantonio.restapp.data.AppService
import com.lnsergioantonio.restapp.data.createNetworkClient
import com.lnsergioantonio.restapp.domain.NetworkSourceImpl
import com.lnsergioantonio.restapp.domain.SendRequestRepositoryImpl
import com.lnsergioantonio.restapp.domain.SendRequestUseCase
import com.lnsergioantonio.restapp.ext.NetworkHandler
import com.lnsergioantonio.restapp.ext.onItemSelectedChanged
import com.lnsergioantonio.restapp.ext.value
import com.lnsergioantonio.restapp.ui.AddHeadersActivity
import com.lnsergioantonio.restapp.ui.HEADERS_LIST
import com.lnsergioantonio.restapp.ui.RESULT_HEADERS_LIST
import com.lnsergioantonio.restapp.ui.fragment.dialog.*
import com.lnsergioantonio.restapp.ui.adapter.HeadersItem
import kotlinx.android.synthetic.main.fragment_request.*

class RequestFragment : Fragment() {
    private val REQUEST_FRAGMENT = "REQUEST_FRAGMENT"
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
        val apiService = createNetworkClient(BuildConfig.DEBUG).create(AppService::class.java)
        val networkHandler = NetworkHandler(requireContext())
        val networkSource = NetworkSourceImpl(apiService)

        val repository = SendRequestRepositoryImpl(networkHandler, networkSource)
        val useCase = SendRequestUseCase(repository)

        viewModel = RequestViewModel(useCase)
    }

    private fun startHeaderScreen() {
        val headersItems: ArrayList<HeadersItem> = arrayListOf()
        val intent = Intent(requireActivity(), AddHeadersActivity::class.java)

        intent.putParcelableArrayListExtra(HEADERS_LIST, headersItems)
        startActivityForResult(intent,RESULT_HEADERS_LIST)
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