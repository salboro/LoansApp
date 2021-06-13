package com.example.loansapp.domain.usecase

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(
    private val loansRepository: LoansRepository
) {
    operator fun invoke(newLoan: NewLoan): Single<ResultType<Loan>> =
        loansRepository.create(newLoan)
}