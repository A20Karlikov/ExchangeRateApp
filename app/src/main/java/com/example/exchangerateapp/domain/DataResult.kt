package com.example.exchangerateapp.domain

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val error: Exception) : DataResult<Nothing>()
}

suspend fun <R> asDataResult(block: suspend () -> R): DataResult<R> {
    return try {
        DataResult.Success(block())
    } catch (exception: Exception) {
        DataResult.Failure(exception)
    }
}