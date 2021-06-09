package com.example.loansapp.domain.entity

sealed class ResultType<T> {
    data class Success<T>(
        val data: T
    ) : ResultType<T>()

    data class Error<T>(
        val error: ErrorType
    ) : ResultType<T>()
}
