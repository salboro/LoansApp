package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserInformationRepository
import javax.inject.Inject

class UserInformationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserInformationRepository {
    override fun setLocale(langCode: String) {
        localDataSource.setUserPreferLocale(langCode)
    }

    override fun setTheme(theme: ThemeType) {
        localDataSource.setUserPreferTheme(theme)
    }

    override fun getTheme(): ThemeType =
        localDataSource.getUserPreferTheme()

    override fun setName(name: String) {
        localDataSource.setUserName(name)
    }

    override fun checkFirstLaunch(): Boolean =
        localDataSource.checkFirstLaunch()

}