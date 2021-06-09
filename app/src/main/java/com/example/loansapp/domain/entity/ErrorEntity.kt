package com.example.loansapp.domain.entity

sealed class ErrorEntity {
    object Network : ErrorEntity()

    object Unauthorized : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}