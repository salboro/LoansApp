package com.example.loansapp.domain

import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserInformationRepository
import com.example.loansapp.domain.usecase.GetUserPreferThemeUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserPreferThemeUseCaseTest {
    private val themeType = ThemeType.Light

    private val userInformationRepository: UserInformationRepository = mock()
    private val getUserPreferThemeUseCase = GetUserPreferThemeUseCase(userInformationRepository)

    @Test
    fun `WHEN getUserPreferThemeUseCase() EXCEPT themeType`() {
        whenever(userInformationRepository.getTheme()).thenReturn(themeType)

        val theme = getUserPreferThemeUseCase()

        assertEquals(theme, userInformationRepository.getTheme())
    }
}