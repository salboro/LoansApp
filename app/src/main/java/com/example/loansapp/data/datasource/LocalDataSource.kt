package com.example.loansapp.data.datasource

interface LocalDataSource {
    fun saveBearerToken(token: String)

    fun getBearerToken(): String
}