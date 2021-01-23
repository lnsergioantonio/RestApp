package com.lnsergioantonio.restapp.domain

import com.lnsergioantonio.restapp.domain.base.State
import com.lnsergioantonio.restapp.domain.model.ResponseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SendRequestUseCase(private val repository: SendRequestRepository) {
    fun invoke(
            scope: CoroutineScope,
            params: Params,
            //onResult: (State<ResponseEntity>) -> Unit = {}
            //onResult: (MutableList<Flow<State<ResponseEntity>>>) -> Unit = {}
            onResult: (MutableList<State<ResponseEntity>>) -> Unit = {}
    ) {
        val allResponseFromApi = mutableListOf<State<ResponseEntity>>()
        val callFromApi = mutableListOf<State<ResponseEntity>>()
        //val callFromApi = mutableListOf<Flow<State<ResponseEntity>>>()
        scope.launch {
            val jobs = Array(params.numberCalls){
                scope.async {
                    run(params)
                }
            }
            jobs.forEach {
                //callFromApi.add() }
                callFromApi.add(it.await())
            }
            onResult(callFromApi)
        }

        /*for (i in 1..params.numberCalls){
            scope.launch {
                    val backgroundJob = async {
                        run(params)
                    }
                    backgroundJob
                            .await()
                            .onStart {
                                emit(State.Progress(isLoading = true))
                            }.catch {
                                emit(State.Failure(it))
                            }
                            .collect {
                                callFromApi.add(it)
                            }

                    //callFromApi.add(backgroundJob.await())

                    //val response: Flow<State<ResponseEntity>> = backgroundJob.await()
                    *//*withContext(scope.coroutineContext) {
                        run(params)
                    }.collect {
                        allResponseFromApi.add(it)
                    }*//*
                onResult(callFromApi)
            }
        }*/
        /*scope.launch {
            val backgroundJob = async {
                run(params)
            }
            backgroundJob
                    .await()
                    .collect {
                        callFromApi.add(it)
                    }

            //callFromApi.add(backgroundJob.await())

            //val response: Flow<State<ResponseEntity>> = backgroundJob.await()
            *//*withContext(scope.coroutineContext) {
                run(params)
            }.collect {
                allResponseFromApi.add(it)
            }*//*
                    backgroundJob.await().shareIn(scope, SharingStarted.Eagerly)
            onResult(callFromApi)*/
        //resultShared(params.numberCalls, params, scope, onResult)

        /*val resultShared = MutableSharedFlow<State<ResponseEntity>>(params.numberCalls)
        (1 .. params.numberCalls).forEach {
            scope.launch {
                val backgroundJob = async {
                    run(params)
                }
                backgroundJob.await().shareIn(scope, SharingStarted.Eagerly).collect {
                    callFromApi.add(it)
                }
                onResult(callFromApi)
            }
        }*/
    }

    /*private fun resultShared(counter: Int, params: Params, scope: CoroutineScope, onResult: (State<ResponseEntity>) -> Unit){
        val resultShared = MutableSharedFlow<State<ResponseEntity>>(params.numberCalls)
        scope.launch {
            (1 .. counter).forEach {
                val backgroundJob = async {
                    run(params).collect {
                                resultShared.emit(it)
                            }
                }.await()
                resultShared.collect { response ->
                    onResult(response)
                }
            }
            /*resultShared.collect {
                onResult(it)
            }*/
        }
    }*/

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