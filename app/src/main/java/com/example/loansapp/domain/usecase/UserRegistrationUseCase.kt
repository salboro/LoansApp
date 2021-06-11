package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.AuthorizationRepository
import com.example.loansapp.domain.entity.AuthorizeResultType
import io.reactivex.Single
import javax.inject.Inject

class UserRegistrationUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {

    operator fun invoke(name: String, password: String): Single<AuthorizeResultType> =
        repository.register(name, password)

}