package com.example.loansapp.domain.usecase

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCachedLoansUseCase @Inject constructor(
    private val loansRepository: LoansRepository
) {

    operator fun invoke(): Single<List<Loan>> =
        loansRepository.getAllCached()
}