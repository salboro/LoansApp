package com.example.loansapp.presentation.enter.login

import com.example.loansapp.domain.entity.ErrorType

sealed class LoginViewState {
    object SuccessAuthorised : LoginViewState()

    object Loading : LoginViewState()

    data class Error(
        val reason: ErrorType
    ) : LoginViewState()
}
