package com.lnsergioantonio.restapp.domain.model

data class ResponseEntity(
        val id: Int = 0,
        val description: String,
        val method: String,
        val url: String,
        val time: String,
        val statusCode: Int,
        val size: Long,
        val requestBody: String,
        val responseBody: String,
        val isSuccessful: Boolean,
        val error: String,
        val reqTime: Long,
        val resTime: Long
)
