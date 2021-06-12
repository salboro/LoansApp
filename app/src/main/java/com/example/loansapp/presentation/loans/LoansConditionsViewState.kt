package com.example.loansapp.presentation.loans

import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ErrorType

sealed class LoansConditionsViewState {
    object Loading : LoansConditionsViewState()

    data class Success(
        val loansConditions: LoansConditions
    ) : LoansConditionsViewState()

    data class Error(
        val reason: ErrorType
    ) : LoansConditionsViewState()
}