package com.example.loansapp.data.datasource

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.example.loansapp.data.db.LoansDatabaseDao
import com.example.loansapp.data.db.entity.LoanDb
import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ThemeType
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalDataSourceTest {
    companion object {
        private const val BEARER_TOKEN_STRING_NAME = "AUTHORIZATION TOKEN"
        private const val PREFER_THEME = "PREFER THEME"
        private const val PREFER_LOCALE = "PREFER LOCALE"
        private const val USER_NAME = "USER NAME"
        private const val FIRST_LAUNCH = "FIRST LAUNCH"
    }

    private val langCode = "en"
    private val token = "test"
    private val themeType = ThemeType.Light
    private val themeTypeString = "light"
    private val userName = "test"

    private val loanDb = LoanDb(
        userName = userName,
        id = 1,
        firstName = "test",
        lastName = "test",
        amount = 10.0,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
        date = "test",
        state = "APPROVED"
    )

    private val loan = Loan(
        id = 1,
        firstName = "test",
        lastName = "test",
        amount = 10.0,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
        date = "test",
        state = "APPROVED"
    )

    private val preferences: EncryptedSharedPreferences = mock()
    private val database: LoansDatabaseDao = mock()

    private var localDataSource = LocalDataSourceImpl(preferences, database)

    @Test
    fun `WHEN save bearer token EXPECT just runs`() {
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesEditor.putString(BEARER_TOKEN_STRING_NAME, token)).thenReturn(
            sharedPreferencesEditor
        )
        doNothing().whenever(sharedPreferencesEditor).apply()
        whenever(preferences.edit()).thenReturn(sharedPreferencesEditor)

        localDataSource.saveBearerToken(token)

        verify(sharedPreferencesEditor).putString(BEARER_TOKEN_STRING_NAME, token)
    }

    @Test
    fun `WHEN get bearer token EXPECT returns token`() {
        whenever(preferences.getString(BEARER_TOKEN_STRING_NAME, "")).thenReturn(token)

        val bearerToken = localDataSource.getBearerToken()

        assertEquals(preferences.getString(BEARER_TOKEN_STRING_NAME, ""), bearerToken)
    }

    @Test
    fun `WHEN set user prefer locale EXPECT just runs`() {
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesEditor.putString(PREFER_LOCALE, langCode)).thenReturn(
            sharedPreferencesEditor
        )
        doNothing().whenever(sharedPreferencesEditor).apply()
        whenever(preferences.edit()).thenReturn(sharedPreferencesEditor)

        localDataSource.setUserPreferLocale(langCode)

        verify(sharedPreferencesEditor).putString(PREFER_LOCALE, langCode)
    }

    @Test
    fun `WHEN set user prefer theme EXPECT just runs`() {
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesEditor.putString(PREFER_THEME, themeTypeString)).thenReturn(
            sharedPreferencesEditor
        )
        doNothing().whenever(sharedPreferencesEditor).apply()
        whenever(preferences.edit()).thenReturn(sharedPreferencesEditor)

        localDataSource.setUserPreferTheme(themeType)

        verify(sharedPreferencesEditor).putString(PREFER_THEME, themeTypeString)
    }

    @Test
    fun `WHEN set user name EXPECT just runs`() {
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesEditor.putString(USER_NAME, userName)).thenReturn(
            sharedPreferencesEditor
        )
        doNothing().whenever(sharedPreferencesEditor).apply()
        whenever(preferences.edit()).thenReturn(sharedPreferencesEditor)

        localDataSource.setUserName(userName)

        verify(sharedPreferencesEditor).putString(USER_NAME, userName)
    }

    @Test
    fun `WHEN get loans EXPECT return list of loans`() {
        whenever(database.getLoans(userName)).thenReturn(Single.just(listOf(loanDb)))
        whenever(preferences.getString(USER_NAME, "")).thenReturn(userName)

        val observer: SingleObserver<List<Loan>> = mock()
        localDataSource.getLoans().subscribeWith(observer)

        verify(observer).onSuccess(listOf(loan))
    }

    @Test
    fun `WHEN add loans EXPECT complete`() {
        whenever(database.addLoans(listOf(loanDb))).thenReturn(Completable.complete())
        whenever(preferences.getString(USER_NAME, "")).thenReturn(userName)


        val observer: CompletableObserver = mock()
        localDataSource.addLoans(listOf(loan)).subscribeWith(observer)

        verify(observer).onComplete()
    }

    @Test
    fun `WHEN check first launch EXCEPT returns boolean`() {
        val sharedPreferencesEditor: SharedPreferences.Editor = mock()
        whenever(sharedPreferencesEditor.putBoolean(FIRST_LAUNCH, false)).thenReturn(
            sharedPreferencesEditor
        )
        doNothing().whenever(sharedPreferencesEditor).apply()
        whenever(preferences.edit()).thenReturn(sharedPreferencesEditor)

        whenever(preferences.getBoolean(FIRST_LAUNCH, true)).thenReturn(true)

        val isFirstLaunch = localDataSource.checkFirstLaunch()

        assertEquals(preferences.getBoolean(FIRST_LAUNCH, true), isFirstLaunch)
    }
}