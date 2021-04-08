package com.lnsergioantonio.restapp.domain.network

import com.lnsergioantonio.restapp.domain.model.RequestEntity
import com.lnsergioantonio.restapp.data.local.dao.RequestResponseDao
import com.lnsergioantonio.restapp.data.local.entitues.RequestResponseEntity
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
            headerList: Map<String, String>,
            url: String,
            method: String,
            body: String,
            bodyType: String
    ): State<ResponseEntity>
}
// https://jsonplaceholder.typicode.com/todos/1
class SendRequestRepositoryImpl(
        private val networkHandler: NetworkHandler,
        private val networkSource: NetworkSource,
        private val dao: RequestResponseDao
) : SendRequestRepository {

    override suspend fun fetchData(
            headerList: Map<String, String>,
            url: String,
            method: String,
            body: String,
            bodyType: String
    ): State<ResponseEntity> {
        return try {
            fetch(headerList, url, method, body, bodyType)
        } catch (e: Exception) {
            State.Failure(e)
        }
    }

    private suspend fun fetch(
            headerList: Map<String, String>,
            url: String,
            method: String,
            body: String,
            bodyType: String
    ): State<ResponseEntity> {
        return when (networkHandler.isConnected) {
            true -> {
                val requestBody = getRequestBody(body, bodyType)
                val request = RequestEntity(headerList, url, method, requestBody, body)
                val id:Long = dao.insert(request.toEntity())
                val result = networkSource.fetchData(request)
                dao.update(
                        id,
                        requestAt = result.reqTime.toString(),
                        responseAt = result.resTime.toString(),
                        time = result.time,
                        statusCode = result.statusCode,
                        size = result.size,
                        responseBody = result.responseBody,
                        isSuccessful = if (result.isSuccessful) 1 else 0,
                        error = result.error)
                State.Success(result)
            }
            else -> {
                State.Failure(NetworkConnectionException())
            }
        }
    }

    private fun getRequestBody(body: String, bodyType: String): RequestBody {
        return when (bodyType) {
            JSON ->
                body.toRequestBody(MEDIA_TYPE_JSON)
            else ->
                body.toRequestBody(MEDIA_TYPE_TEXT)
        }
    }
}

private fun RequestEntity.toEntity(): RequestResponseEntity {
    return RequestResponseEntity(
            description = "descr",
            headerList = this.headerList.toString(),
            method = method,
            url = url,
            requestAt = "",
            responseAt = "",
            time = "0",
            statusCode = 0,
            size = 0,
            requestBody = sBody,
            responseBody = "",
            isSuccessful = 0,
            error = ""
    )
}


const val JSON = "JSON"
const val TEXT = "Text"
val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
val MEDIA_TYPE_TEXT = "text/plain; charset=utf-8".toMediaTypeOrNull()