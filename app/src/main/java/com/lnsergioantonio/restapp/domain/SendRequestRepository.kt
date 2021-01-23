package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.data.RequestEntity
import com.lnsergioantonio.restapp.domain.base.NetworkConnectionException
import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import com.lnsergioantonio.restapp.ext.NetworkHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

interface SendRequestRepository {
    suspend fun fetchData(
        headerList: Map<String,String>,
        url: String,
        method: String,
        body: String,
        bodyType: String
    ):State<ResponseEntity>
}

class SendRequestRepositoryImpl(
    private val networkHandler: NetworkHandler,
    private val networkSource: NetworkSource
) : SendRequestRepository {

    override suspend fun fetchData(
        headerList: Map<String,String>,
        url: String,
        method: String,
        body: String,
        bodyType: String
    ): State<ResponseEntity> {
        return try {
            fetch(headerList, url, method, body, bodyType)
        }catch (e:Exception){
            State.Failure(e)
        }
        //emit(result)
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
                body.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            else ->
                body.toRequestBody("text/plain; charset=utf-8".toMediaTypeOrNull())
        }
    }
}

const val JSON = "JSON"
const val TEXT = "Text"