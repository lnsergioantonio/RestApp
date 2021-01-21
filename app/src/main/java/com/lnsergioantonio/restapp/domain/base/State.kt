package com.lnsergioantonio.restapp.domain.base

sealed class State<out T : Any> {
    data class Success<out T : Any>(val data: T) : State<T>()

    data class Failure(
        val exception: Throwable,
        val message: String = exception.message ?: ErrorHandler.UNKNOWN_ERROR
    ) : State<Nothing>()

    data class Progress(val isLoading: Boolean) : State<Nothing>()
}