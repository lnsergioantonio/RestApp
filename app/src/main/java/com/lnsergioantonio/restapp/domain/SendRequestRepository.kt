package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.BuildConfig
import com.lnsergioantonio.restapp.data.RequestEntity
import com.lnsergioantonio.restapp.domain.base.NetworkConnectionException
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import com.lnsergioantonio.restapp.ext.NetworkHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface SendRequestRepository {
    fun fetchData(
        headerList: Map<String,String>,
        url: String,
        method: String,
        body: String,
        bodyType: String
    ): Flow<State<ResponseEntity>>
}

class SendRequestRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val networkSource: NetworkSource
) : SendRequestRepository {

    override fun fetchData(
        headerList: Map<String,String>,
        url: String,
        method: String,
        body: String,
        bodyType: String
    ) = flow {
        val result = fetch(headerList, url, method, body, bodyType)
        emit(result)
    }

    private suspend fun fetch(
        headerList: Map<String,String>,
        url: String,
        method: String,
        body: String,
        bodyType: String
    ): State<ResponseEntity> {
        return when (networkHandler.isConnected) {
            true -> {
                val requestBody = getRequestBody(body, bodyType)
                val request = RequestEntity(headerList, url, method, requestBody)
                State.Success(networkSource.fetchData(request))
            }
            else -> {
                State.Failure(NetworkConnectionException())
            }
        }
    }

    private fun getRequestBody(body: String, bodyType: String): RequestBody {
        return when (bodyType) {
            JSON ->
                bodyType.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            else ->
                bodyType.toRequestBody("text/plain; charset=utf-8".toMediaTypeOrNull())
        }
    }
}

const val JSON = "JSON"
const val TEXT = "Text"