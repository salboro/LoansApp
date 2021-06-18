package com.example.loansapp.data.datasource

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ThemeType
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {
    fun saveBearerToken(token: String)

    fun getBearerToken(): String

    fun setUserPreferLocale(langCode: String)

    fun setUserPreferTheme(theme: ThemeType)

    fun getUserPreferTheme(): ThemeType

    fun setUserName(name: String)

    fun getLoans(): Single<List<Loan>>

    fun addLoans(loans: List<Loan>): Completable

    fun checkFirstLaunch(): Boolean
}