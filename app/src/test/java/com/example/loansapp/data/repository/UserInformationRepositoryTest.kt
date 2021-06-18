package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.domain.entity.ThemeType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserInformationRepositoryTest {
    private val name = "test"
    private val langCode = "en"
    private val theme = ThemeType.Light

    private val localDataSource: LocalDataSource = mock()
    private val userInformationRepository = UserInformationRepositoryImpl(localDataSource)

    @Test
    fun `WHEN set locale EXPECT just runs`() {
        doNothing().whenever(localDataSource).setUserPreferLocale(langCode)

        userInformationRepository.setLocale(langCode)

        verify(localDataSource).setUserPreferLocale(langCode)
    }

    @Test
    fun `WHEN set theme EXPECT just runs`() {
        doNothing().whenever(localDataSource).setUserPreferTheme(theme)

        userInformationRepository.setTheme(theme)

        verify(localDataSource).setUserPreferTheme(theme)
    }

    @Test
    fun `WHEN get theme EXPECT returns one of the theme type`() {
        whenever(localDataSource.getUserPreferTheme()).thenReturn(theme)

        val themeType = userInformationRepository.getTheme()

        assertEquals(localDataSource.getUserPreferTheme(), themeType)
    }

    @Test
    fun `WHEN set name EXPECT just runs`() {
        doNothing().whenever(localDataSource).setUserName(name)

        userInformationRepository.setName(name)

        verify(localDataSource).setUserName(name)
    }

    @Test
    fun `WHEN check first launch EXPECT returns boolean`() {
        whenever(localDataSource.checkFirstLaunch()).thenReturn(true)

        val isFirstLaunch = localDataSource.checkFirstLaunch()

        assertEquals(localDataSource.checkFirstLaunch(), isFirstLaunch)
    }
}