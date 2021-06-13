package com.example.loansapp.domain.usecase

import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLoansConditionsUseCase @Inject constructor(
    private val loansRepository: LoansRepository
) {

    operator fun invoke(): Single<ResultType<LoansConditions>> =
        loansRepository.getConditions()
}