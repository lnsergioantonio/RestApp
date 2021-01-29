package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.data.AppService
import com.lnsergioantonio.restapp.data.RequestEntity
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import okhttp3.ResponseBody
import retrofit2.Response

interface NetworkSource {
    suspend fun fetchData(request: RequestEntity): ResponseEntity
}

class NetworkSourceImpl(private val api: AppService) : NetworkSource {
    override suspend fun fetchData(request: RequestEntity): ResponseEntity {
        return when (request.method) {
            "POST" -> {
                api.fetchPost(request.url, request.headerList, request.body).toDomain()
            }
            else -> {
                api.fetchGet(request.url, request.headerList).toDomain()
            }
        }
    }
}

private fun Response<ResponseBody>.toDomain(): ResponseEntity {
    val reqTime: Long = raw().sentRequestAtMillis
    val resTime: Long = raw().receivedResponseAtMillis
    val method: String = raw().request.method
    val requestBody: String = raw().request.body.toString()
    val uri: String = raw().request.url.encodedPath
    var rawResponseBody = ""
    var error = ""

    if (isSuccessful)
        rawResponseBody = body()?.string() ?: ""
    else
        error = errorBody()?.string() ?: ""

    val sizeBody = body()?.contentLength()?:0

    val size = if (sizeBody > 0) sizeBody/1024 else 0
    return ResponseEntity(
            statusCode = code(),
            time = "${resTime - reqTime} ms",
            size = size,
            responseBody = rawResponseBody,
            isSuccessful = isSuccessful,
            error = error,
            description = "",
            id = 0,
            method = method,
            requestBody = requestBody,
            url = uri,
            reqTime = reqTime,
            resTime = resTime
    )
}