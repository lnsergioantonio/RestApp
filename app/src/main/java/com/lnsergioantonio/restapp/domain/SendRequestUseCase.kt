package com.lnsergioantonio.restapp.domain

class SendRequestUseCase() {

    data class Params(
        val headerList: List<Map<String,String>>,
        val method: String,
        val body: String,
        val bodyType: String
    )
}