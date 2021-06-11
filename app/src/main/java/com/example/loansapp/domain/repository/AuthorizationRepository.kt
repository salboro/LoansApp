package com.example.loansapp.domain.repository

import com.example.loansapp.domain.entity.AuthorizeResultType
import io.reactivex.Single

interface AuthorizationRepository {
     fun login(name: String, password: String): Single<AuthorizeResultType>

     fun register(name: String, password: String): Single<AuthorizeResultType>
}