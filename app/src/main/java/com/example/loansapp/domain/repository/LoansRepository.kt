package com.example.loansapp.domain.repository

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single

interface LoansRepository {
    fun getLoans(): Single<ResultType<List<Loan>>>

    fun getLoansConditions(): Single<ResultType<LoansConditions>>
}