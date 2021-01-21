package com.lnsergioantonio.restapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val timeOut = 20L //20Secs//

fun createNetworkClient(baseUrl: String, debug: Boolean = false) =
    retrofitClient(
        baseUrl,
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

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        //.baseUrl(baseUrl) // colocar luego esto en el config
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient) // tratar de hacer esto un factory del client
        .build()
