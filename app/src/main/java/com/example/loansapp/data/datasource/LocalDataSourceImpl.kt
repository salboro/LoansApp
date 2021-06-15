package com.example.loansapp.data.datasource

import android.content.SharedPreferences
import com.example.loansapp.domain.entity.ThemeType
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
) : LocalDataSource {

    companion object {
        private const val BEARER_TOKEN_STRING_NAME = "AUTHORIZATION TOKEN"
        private const val PREFER_THEME = "PREFER THEME"
        private const val PREFER_LOCALE = "PREFER LOCALE"
    }

    override fun saveBearerToken(token: String) {
        preferences.edit()
            .putString(BEARER_TOKEN_STRING_NAME, token)
            .apply()
    }

    override fun getBearerToken(): String =
        preferences.getString(BEARER_TOKEN_STRING_NAME, "") ?: ""

    override fun getUserPreferLocale(): String =
        preferences.getString(PREFER_LOCALE, "") ?: ""


    override fun setUserPreferLocale(langCode: String) {
        preferences.edit()
            .putString(PREFER_LOCALE, langCode)
            .apply()
    }

    override fun setUserPreferTheme(theme: ThemeType) {
        val themeString = if (theme is ThemeType.Dark) "dark" else "light"

        preferences.edit()
            .putString(PREFER_THEME, themeString)
            .apply()
    }

    override fun getUserPreferTheme(): ThemeType {
        val themeString = preferences.getString(PREFER_THEME, "light") ?: "light"

        return when (themeString) {
            "dark" -> ThemeType.Dark

            "light" -> ThemeType.Light

            else -> throw Exception("Illegal theme string")
        }
    }

}