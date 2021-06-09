package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.LoansRepository
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val repository: LoansRepository
) {
    operator fun invoke(name: String, password: String): Single<ResultType<String>> =
        repository.login(name, password)
}