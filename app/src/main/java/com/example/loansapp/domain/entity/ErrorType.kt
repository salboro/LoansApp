package com.example.loansapp.domain.entity

sealed class ErrorType {
    object Network : ErrorType()

    object NotFound : ErrorType()

    object InvalidData : ErrorType()

    object AccessDenied : ErrorType()

    object Connection : ErrorType()

    object Unknown : ErrorType()
}