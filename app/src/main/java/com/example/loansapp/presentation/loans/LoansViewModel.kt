package com.example.loansapp.presentation.loans

import com.example.loansapp.domain.usecase.GetLoansConditionsUseCase
import com.example.loansapp.domain.usecase.GetLoansUseCase
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getLoansUseCase: GetLoansUseCase,
    private val getLoansConditionsUseCase: GetLoansConditionsUseCase
) : BaseViewModel() {

    fun getLoans() {

    }

    fun getLoansConditions() {

    }
}