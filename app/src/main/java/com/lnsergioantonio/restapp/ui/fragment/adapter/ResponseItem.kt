package com.lnsergioantonio.restapp.ui.fragment.adapter

import com.lnsergioantonio.restapp.domain.model.ResponseEntity

data class ResponseItem(
        val description: String,
        val method: String,
        val uri: String,
        val statusCode: Int,
        val time: String,
        val size: Long,
        val body: String,
        val isSuccessful: Boolean,
        val error: String
)

fun List<ResponseEntity>.toItems(): List<ResponseItem> {
    return map {
        ResponseItem(
                description = it.description,
                method = it.method,
                uri = it.url,
                statusCode = it.statusCode,
                time = it.time,
                size = it.size,
                body = it.responseBody,
                isSuccessful = it.isSuccessful,
                error = ""
        )
    }
}