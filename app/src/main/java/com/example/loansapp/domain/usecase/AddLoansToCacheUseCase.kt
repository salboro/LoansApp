package com.example.loansapp.domain.usecase

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddLoansToCacheUseCase @Inject constructor(
    private val loansRepository: LoansRepository
) {

    operator fun invoke(loans: List<Loan>): Completable =
        loansRepository.addToCache(loans)
}