package com.example.loansapp.presentation.authorization.login

import com.example.loansapp.domain.entity.ErrorType

sealed class LoginViewState {
    object SuccessAuthorised : LoginViewState()

    object Loading : LoginViewState()

    data class Error(
        val reason: ErrorType
    ) : LoginViewState()
}
