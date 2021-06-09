package com.example.loansapp.data.datasource

import android.content.SharedPreferences
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
) : LocalDataSource {

    companion object {
        private const val BEARER_TOKEN_STRING_NAME = "AUTHORIZATION TOKEN"
    }

    override fun saveBearerToken(token: String) {
        preferences.edit()
            .putString(BEARER_TOKEN_STRING_NAME, token)
            .apply()
    }

    override fun getBearerToken(): Single<String> = Single.just(
        preferences.getString(BEARER_TOKEN_STRING_NAME, "") ?: ""
    )
}