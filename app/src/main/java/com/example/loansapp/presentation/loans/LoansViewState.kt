package com.example.loansapp.presentation.loans

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ErrorType

sealed class LoansViewState {
    object Loading : LoansViewState()

    data class Success(
        val loans: List<Loan>
    ) : LoansViewState()

    data class Error(
        val reason: ErrorType
    ) : LoansViewState()
}