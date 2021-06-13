package com.example.loansapp.presentation.createloan

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ErrorType

sealed class CreateLoanViewState {
    object Loading : CreateLoanViewState()

    data class Success(
        val loan: Loan
    ) : CreateLoanViewState()

    data class Error(
        val reason: ErrorType
    ) : CreateLoanViewState()
}
