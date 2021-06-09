package com.example.loansapp.domain

import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single

interface AuthorizationRepository {
     fun login(name: String, password: String): Single<AuthorizeResultType>

     fun register(name: String, password: String): Single<ResultType<RegistrationRequest>>
}