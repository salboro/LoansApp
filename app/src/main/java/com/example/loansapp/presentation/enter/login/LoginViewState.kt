package com.example.loansapp.presentation.enter.login

import com.example.loansapp.domain.entity.ErrorEntity

sealed class LoginViewState {
    object Default: LoginViewState()

    data class SuccessAuthorised(
        val token: String
    ): LoginViewState()

    data class Error(
        val reason: ErrorEntity
    ): LoginViewState()
}
