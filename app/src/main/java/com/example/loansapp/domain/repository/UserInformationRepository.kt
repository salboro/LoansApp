package com.example.loansapp.domain.repository

import com.example.loansapp.domain.entity.ThemeType

interface UserInformationRepository {
    fun setLocale(langCode: String)

    fun setTheme(theme: ThemeType)

    fun getTheme(): ThemeType

    fun setName(name: String)

    fun checkFirstLaunch(): Boolean
}
