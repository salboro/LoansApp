package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : LoansRepository {
    private val bearerToken: String = localDataSource.getBearerToken()

    override fun getAll(): Single<ResultType<List<Loan>>> =
        remoteDataSource.getLoans(bearerToken).subscribeOn(Schedulers.io())


    override fun getConditions(): Single<ResultType<LoansConditions>> =
        remoteDataSource.getLoansConditions(bearerToken).subscribeOn(Schedulers.io())

    override fun create(newLoan: NewLoan): Single<ResultType<Loan>> = remoteDataSource.createLoan(
        newLoan,
        bearerToken
    ).subscribeOn(Schedulers.io())

}