package com.lnsergioantonio.restapp.data.local.entitues

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "request_response")
data class RequestResponseEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val description: String,
        val headerList: String,
        val method: String,
        val url: String,
        val requestAt: String,
        val responseAt: String,
        val time: String,
        val statusCode: Int,
        val size: Long,
        val requestBody: String,
        val responseBody: String,
        val isSuccessful: Int,
        val error: String
)