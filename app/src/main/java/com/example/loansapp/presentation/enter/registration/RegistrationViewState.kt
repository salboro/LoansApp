package com.example.loansapp.presentation.enter.registration

import com.example.loansapp.domain.entity.ErrorType

sealed class RegistrationViewState {
    object SuccessRegistered : RegistrationViewState()

    object Loading : RegistrationViewState()

    data class Error(
        val reason: ErrorType
    ) : RegistrationViewState()
}
