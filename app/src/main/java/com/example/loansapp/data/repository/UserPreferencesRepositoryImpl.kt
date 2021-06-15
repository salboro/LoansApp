package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserPreferencesRepository {
    override fun getLocale(): String =
        localDataSource.getUserPreferLocale()

    override fun setLocale(langCode: String) {
        localDataSource.setUserPreferLocale(langCode)
    }

    override fun setTheme(theme: ThemeType) {
        localDataSource.setUserPreferTheme(theme)
    }

    override fun getTheme(): ThemeType =
        localDataSource.getUserPreferTheme()
}