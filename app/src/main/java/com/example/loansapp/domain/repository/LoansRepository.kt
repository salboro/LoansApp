package com.example.loansapp.domain.repository

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Completable
import io.reactivex.Single

interface LoansRepository {
    fun getAll(): Single<ResultType<List<Loan>>>

    fun getAllCached(): Single<List<Loan>>

    fun addToCache(loans: List<Loan>): Completable

    fun getConditions(): Single<ResultType<LoansConditions>>

    fun create(newLoan: NewLoan): Single<ResultType<Loan>>
}