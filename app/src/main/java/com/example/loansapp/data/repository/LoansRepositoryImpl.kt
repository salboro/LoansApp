package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoansRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : LoansRepository {

    override fun getAll(): Single<ResultType<List<Loan>>> =
        remoteDataSource.getLoans(localDataSource.getBearerToken()).subscribeOn(Schedulers.io())


    override fun getConditions(): Single<ResultType<LoansConditions>> =
        remoteDataSource.getLoansConditions(localDataSource.getBearerToken())
            .subscribeOn(Schedulers.io())

    override fun create(newLoan: NewLoan): Single<ResultType<Loan>> = remoteDataSource.createLoan(
        newLoan,
        localDataSource.getBearerToken()
    ).subscribeOn(Schedulers.io())

    override fun getAllCached(): Single<List<Loan>> =
        localDataSource.getLoans().subscribeOn(Schedulers.io())

    override fun addToCache(loans: List<Loan>): Completable =
        localDataSource.addLoans(loans).subscribeOn(Schedulers.io())

}