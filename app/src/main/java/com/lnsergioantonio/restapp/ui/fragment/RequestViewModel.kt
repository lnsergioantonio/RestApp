package com.lnsergioantonio.restapp.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnsergioantonio.restapp.domain.SendRequestUseCase
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import com.lnsergioantonio.restapp.ui.header.adapter.HeadersItem
import java.util.ArrayList

class RequestViewModel(private val sendRequestUseCase: SendRequestUseCase) : ViewModel() {

    private val liveDataRequest = MutableLiveData<Request>()
    val requestState:LiveData<Request> get() = liveDataRequest

    private val responseLiveData = MutableLiveData<List<State<ResponseEntity>>>()
    val responseState: LiveData<List<State<ResponseEntity>>> get() = responseLiveData

    init {
        liveDataRequest.value = Request()
    }

    fun sendRequest() {
        liveDataRequest.value?.let { params ->
            sendRequestUseCase.invoke(viewModelScope, params.getParam()) { result ->
                responseLiveData.value = result
            }
        }
    }

    fun setHeader(key: String, value: String) {
        liveDataRequest.value?.apply {
            addHeader(key, value)
        }?.let {
            liveDataRequest.postValue(it)
        }
    }

    fun clearHeaders() {
        liveDataRequest.value?.apply {
            headerList.clear()
        }?.let {
            liveDataRequest.postValue(it)
        }
    }

    fun setRequestUrl(sUrl: String, sNumberCalls: String) {
        liveDataRequest.value?.apply {
            url = sUrl
            numberCalls = sNumberCalls.toIntOrNull() ?: 1
        }?.let {
            liveDataRequest.value = it
        }
    }

    fun setBody(sBody: String, sBodyType: String) {
        liveDataRequest.value?.apply {
            body = sBody
            bodyType = sBodyType
        }?.let {
            liveDataRequest.value = it
        }
    }

    fun setMethod(value: String?) {
        liveDataRequest.value?.apply {
            method = value ?: "GET"
        }?.let {
            liveDataRequest.value = it
        }
    }

    data class Request(
            val headerList: MutableMap<String, String> = HashMap(),
            var url: String = "",
            var method: String = "",
            var body: String = "",
            var bodyType: String = "",
            var numberCalls: Int = 1
    ) {
        fun getParam(): SendRequestUseCase.Params {
            return SendRequestUseCase.Params(headerList, url, method, body, bodyType, numberCalls)
        }

        fun addHeader(key: String, value: String) {
            headerList[key] = value
        }

        fun isEmpty(): Boolean {
            return url.isEmpty() || method.isEmpty()
        }

        fun getHeaders(): ArrayList<HeadersItem> {
            return headerList.map {
                HeadersItem(it.key, it.value)
            } as ArrayList<HeadersItem>
        }
    }
}