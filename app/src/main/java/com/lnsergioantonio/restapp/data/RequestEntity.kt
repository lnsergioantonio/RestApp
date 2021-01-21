package com.lnsergioantonio.restapp.data

import okhttp3.RequestBody

data class RequestEntity(
        val headerList: Map<String,String>,
        val url: String,
        val method: String,
        val body: RequestBody
)
