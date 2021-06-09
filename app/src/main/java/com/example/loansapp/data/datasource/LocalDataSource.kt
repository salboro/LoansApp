package com.example.loansapp.data.datasource

import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {
    fun saveBearerToken(token: String)

    fun getBearerToken(): Single<String>
}