package com.example.loansapp.domain.repository

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single

interface LoansRepository {
    fun getAll(): Single<ResultType<List<Loan>>>

    fun getConditions(): Single<ResultType<LoansConditions>>

    fun create(newLoan: NewLoan): Single<ResultType<Loan>>
}