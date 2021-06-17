package com.example.loansapp.presentation

import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.usecase.GetUserPreferThemeUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MainViewModelTest {
    private val themeType = ThemeType.Light

    private val getUserPreferThemeUseCase: GetUserPreferThemeUseCase = mock()
    private val mainViewModel = MainViewModel(getUserPreferThemeUseCase)

    @Test
    fun `WHEN mainViewModel call getTheme() EXPECT themeType`() {
        whenever(getUserPreferThemeUseCase()).thenReturn(themeType)

        val theme = mainViewModel.getTheme()

        assertEquals(theme, getUserPreferThemeUseCase())
    }
}