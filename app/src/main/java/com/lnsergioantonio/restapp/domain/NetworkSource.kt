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
    var rawResponseBody = ""
    var error = ""

    if (isSuccessful)
        rawResponseBody = body()?.string() ?: ""
    else
        error = errorBody()?.string() ?: ""

    return ResponseEntity(
            statusCode = code(),
            time = "${resTime - reqTime} ms",
            size = headers().size,
            body = rawResponseBody,
            isSuccessful = isSuccessful,
            reqTime = reqTime,
            resTime = resTime,
            error = error
    )
}