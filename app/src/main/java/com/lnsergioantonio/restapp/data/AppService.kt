package com.lnsergioantonio.restapp.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AppService {
    @GET
    suspend fun fetchGet(
        @Url url: String,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

    @POST
    suspend fun fetchPost(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Response<ResponseBody>
}