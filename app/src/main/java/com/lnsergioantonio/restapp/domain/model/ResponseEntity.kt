package com.lnsergioantonio.restapp.domain.model

data class ResponseEntity(
    val statusCode: Int,
    val time: String,
    val size: Int,
    val body: String,
    val isSuccessful: Boolean
)
