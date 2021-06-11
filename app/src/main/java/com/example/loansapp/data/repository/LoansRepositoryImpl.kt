package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Single
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : LoansRepository {
    private val bearerToken: String = localDataSource.getBearerToken()

    override fun getLoans(): Single<ResultType<List<Loan>>> =
        remoteDataSource.getLoans(bearerToken)


    override fun getLoansConditions(): Single<ResultType<LoansConditions>> =
        remoteDataSource.getLoansConditions(bearerToken)
}