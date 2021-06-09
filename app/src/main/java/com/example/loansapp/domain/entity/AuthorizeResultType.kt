package com.example.loansapp.domain.entity

sealed class AuthorizeResultType {
    object Success : AuthorizeResultType()

    data class Error(
        val error: ErrorType
    ) : AuthorizeResultType()
}
