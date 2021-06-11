package com.example.loansapp.data.datasource

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single


interface RemoteDataSource {
    fun login(name: String, password: String): Single<ResultType<String>>

    fun register(name: String, password: String): Single<ResultType<RegistrationRequest>>

    fun getLoans(authorizationToken: String): Single<ResultType<List<Loan>>>

    fun getLoansConditions(authorizationToken: String): Single<ResultType<LoansConditions>>
}