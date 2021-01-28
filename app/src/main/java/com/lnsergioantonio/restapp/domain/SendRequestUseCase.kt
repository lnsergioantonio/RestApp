package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SendRequestUseCase(private val repository: SendRequestRepository) {
    fun invoke(
            scope: CoroutineScope,
            params: Params,
            onResult: (MutableList<State<ResponseEntity>>) -> Unit = {}
    ) {
        val callFromApi = mutableListOf<State<ResponseEntity>>()
        scope.launch {
            val jobs = Array(params.numberCalls) {
                scope.async {
                    run(params)
                }
            }
            jobs.forEach {
                callFromApi.add(it.await())
            }
            onResult(callFromApi)
        }
    }

    private suspend fun run(params: Params): State<ResponseEntity> {
        return repository.fetchData(params.headerList, params.url, params.method, params.body, params.bodyType)
    }

    data class Params(
            val headerList: Map<String, String>,
            val url: String,
            val method: String,
            val body: String,
            val bodyType: String,
            val numberCalls: Int = 1
    )
}