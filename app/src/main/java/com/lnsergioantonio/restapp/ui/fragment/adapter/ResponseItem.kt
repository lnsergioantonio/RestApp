package com.lnsergioantonio.restapp.ui.fragment.adapter

data class ResponseItem(
    val description:String,
    val method:String,
    val uri:String,
    val statusCode: Int,
    val time: String,
    val size: Int,
    val body: String,
    val isSuccessful: Boolean,
    val error: String
)
