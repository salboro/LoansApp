package com.example.loansapp.domain.entity

sealed class ErrorType {
    object Network : ErrorType()

    object Unauthorized : ErrorType()

    object NotFound : ErrorType()

    object AccessDenied : ErrorType()

    object ServiceUnavailable : ErrorType()

    object Unknown : ErrorType()
}