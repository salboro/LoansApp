package com.example.loansapp.domain

import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserInformationRepository
import com.example.loansapp.domain.usecase.SetUserPreferThemeUseCase
import org.junit.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetPreferThemeUseCaseTest {
    private val themeType = ThemeType.Light

    private val userInformationRepository: UserInformationRepository = mock()
    private val setPreferThemeUseCase = SetUserPreferThemeUseCase(userInformationRepository)

    @Test
    fun `WHEN setPreferThemeUseCase(themeType) EXPECT just runs`() {
        doNothing().`when`(userInformationRepository).setTheme(themeType)

        setPreferThemeUseCase(themeType)

        verify(userInformationRepository).setTheme(themeType)
    }
}