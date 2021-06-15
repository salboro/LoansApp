package com.example.loansapp.domain.repository

import com.example.loansapp.domain.entity.ThemeType

interface UserPreferencesRepository {
    fun getLocale(): String

    fun setLocale(langCode: String)

    fun setTheme(theme: ThemeType)

    fun getTheme(): ThemeType
}
