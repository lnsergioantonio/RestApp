package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SendRequestUseCase(private val repository: SendRequestRepository) {
    fun invoke(
            scope: CoroutineScope,
            params: Params,
            onResult: (MutableList<State<ResponseEntity>>) -> Unit = {}
    ) {
        val callFromApi = mutableListOf<State<ResponseEntity>>()
        val jobs: Array<Flow<State<ResponseEntity>>> = Array(params.numberCalls) {
            run(params)
        }

        jobs.forEachIndexed { index, flow ->
            scope.launch {
                flow.collect { emittedElement ->
                    callFromApi.add(emittedElement)
                    onResult(callFromApi)
                }
            }
        }
    }

    private fun run(params: Params): Flow<State<ResponseEntity>> {
        return flow {
            val result = repository.fetchData(params.headerList, params.url, params.method, params.body, params.bodyType)
            emit(result)
        }.onStart {
            emit(State.Progress(true))
        }.catch {
            emit(State.Failure(it))
        }
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