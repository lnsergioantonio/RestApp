package com.lnsergioantonio.restapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val timeOut = 20L //20Secs//

fun createNetworkClient(debug: Boolean = false) =
    retrofitClient(
        httpClient(debug)
    )

private fun httpClient(debug: Boolean): OkHttpClient {
    return OkHttpClient.Builder().apply {
        readTimeout(timeOut, TimeUnit.SECONDS)
        connectTimeout(timeOut, TimeUnit.SECONDS)
        if (debug) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(this)
            }
        }
    }.build()
}

private fun retrofitClient(httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("http://localhost/") // colocar luego esto en el config
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient) // tratar de hacer esto un factory del client
        .build()
