package com.lnsergioantonio.restapp.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Flow<State<Type>>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (State<Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async {
            run(params)
                .onStart {
                    emit(State.Progress(isLoading = true))
                }.catch {
                    emit(State.Failure(it))
                }
        }
        scope.launch {
            backgroundJob.await().collect {
                onResult(it)
            }
        }
    }

    class None
}
