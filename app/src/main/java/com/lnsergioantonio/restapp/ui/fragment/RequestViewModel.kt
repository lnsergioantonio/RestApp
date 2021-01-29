package com.lnsergioantonio.restapp.ui.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lnsergioantonio.restapp.domain.SendRequestUseCase
import com.lnsergioantonio.restapp.domain.base.State

class RequestViewModel(private val sendRequestUseCase: SendRequestUseCase) : ViewModel() {

    private val liveDataRequest = MutableLiveData<Request>()

    init {
        liveDataRequest.value = Request()
    }

    fun sendRequest() {
        liveDataRequest.value?.let { params ->
            sendRequestUseCase.invoke(viewModelScope, params.getParam()) {res->
                res.forEach { result ->
                    when(result){
                        is State.Success -> Log.d("response -> ", result.data.toString())
                        is State.Failure -> Log.d("response -> ", result.message)
                        is State.Progress -> Log.d("response -> ", "isLoading")
                    }
                }
            }
        }
    }

    fun setHeader(key: String, value: String) {
        liveDataRequest.value?.apply {
            addHeader(key, value)
        }?.let {
            liveDataRequest.value = it
        }
    }

    fun setRequestUrl(sUrl: String, sNumberCalls: String ) {
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
            method = value?:"GET"
        }?.let {
            liveDataRequest.value = it
        }
    }

    private data class Request(
            val headerList: MutableMap<String, String> = HashMap(),
            var url: String = "https://api.sigma-alimentos.com/qa/commercial/platform/log/configuration?route=402227",
            var method: String = "",
            var body: String = "",
            var bodyType: String = "",
            var numberCalls: Int = 1
    ){
        fun getParam(): SendRequestUseCase.Params {
            return SendRequestUseCase.Params(headerList, url, method, body, bodyType, numberCalls)
        }

        fun addHeader(key:String, value:String){
            headerList[key] = value
        }
    }
}