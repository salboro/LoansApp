package com.example.loansapp.data.datasource

import android.content.SharedPreferences
import com.example.loansapp.data.db.LoansDatabaseDao
import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ThemeType
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val database: LoansDatabaseDao
) : LocalDataSource {

    companion object {
        private const val BEARER_TOKEN_STRING_NAME = "AUTHORIZATION TOKEN"
        private const val PREFER_THEME = "PREFER THEME"
        private const val PREFER_LOCALE = "PREFER LOCALE"
        private const val USER_NAME = "USER NAME"
        private const val FIRST_LAUNCH = "FIRST LAUNCH"
    }

    override fun saveBearerToken(token: String) {
        preferences.edit()
            .putString(BEARER_TOKEN_STRING_NAME, token)
            .apply()
    }

    override fun getBearerToken(): String =
        preferences.getString(BEARER_TOKEN_STRING_NAME, "") ?: ""

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

    override fun setUserName(name: String) {
        preferences.edit()
            .putString(USER_NAME, name)
            .apply()
    }

    override fun getLoans(): Single<List<Loan>> {
        val name = getUserName()
        return if (name != "") {
            database.getLoans(getUserName()).map {
                it.map { loanDb ->
                    loanDb.convertToLoan()
                }
            }
        } else {
            Single.just(emptyList())
        }
    }


    override fun addLoans(loans: List<Loan>): Completable {
        val name = getUserName()
        return if (name != "") {
            val loansDb = loans.map { it.convertToLoanDb(name) }
            database.addLoans(loansDb)
        } else {
            Completable.error(java.lang.Exception("User name not found"))
        }
    }

    override fun checkFirstLaunch(): Boolean {
        val isFirstLaunch = preferences.getBoolean(FIRST_LAUNCH, true)
        if (isFirstLaunch) {
            preferences.edit()
                .putBoolean(FIRST_LAUNCH, false)
                .apply()
        }
        return isFirstLaunch
    }


    private fun getUserName(): String =
        preferences.getString(USER_NAME, "") ?: ""
}