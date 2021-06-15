package com.example.loansapp.data.datasource

import com.example.loansapp.domain.entity.ThemeType

interface LocalDataSource {
    fun saveBearerToken(token: String)

    fun getBearerToken(): String

    fun getUserPreferLocale(): String

    fun setUserPreferLocale(langCode: String)

    fun setUserPreferTheme(theme: ThemeType)

    fun getUserPreferTheme(): ThemeType
}