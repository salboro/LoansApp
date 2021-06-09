package com.example.loansapp.data

import com.example.loansapp.data.datasource.LoansDataSource
import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.LoansRepository
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoansRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoansDataSource
) : LoansRepository {
    override fun login(name: String, password: String): Single<ResultType<String>> =
        remoteDataSource.login(name, password).subscribeOn(Schedulers.io())


    override fun register(name: String, password: String): Single<ResultType<RegistrationRequest>> =
        remoteDataSource.register(name, password).subscribeOn(Schedulers.io())
}