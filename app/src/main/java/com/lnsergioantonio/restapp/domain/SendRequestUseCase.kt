package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendRequestUseCase(private val repository: SendRequestRepository) {

    fun invoke(
            scope: CoroutineScope,
            params: Params,
            onResult: (MutableList<State<ResponseEntity>>) -> Unit = {}
    ) {
        val allResponseFromApi = mutableListOf<State<ResponseEntity>>()
        scope.launch {
            for (i in 1..params.numberCalls) {
                //val backgroundJob = scope.async { run(params) }
                //callFromApi.add(backgroundJob)
                //val response: Flow<State<ResponseEntity>> = backgroundJob.await()
                withContext(scope.coroutineContext) {
                    run(params)
                }.collect {
                    allResponseFromApi.add(it)
                }
            }
            onResult(allResponseFromApi)
        }
    }

    private fun run(params: Params): Flow<State<ResponseEntity>> {
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