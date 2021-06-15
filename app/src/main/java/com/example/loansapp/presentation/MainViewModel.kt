package com.example.loansapp.presentation

import androidx.lifecycle.ViewModel
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.usecase.GetUserPreferLocaleUseCase
import com.example.loansapp.domain.usecase.GetUserPreferThemeUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserPreferThemeUseCase: GetUserPreferThemeUseCase,
    private val getUserPreferLocaleUseCase: GetUserPreferLocaleUseCase
) : ViewModel() {

    fun getTheme(): ThemeType = getUserPreferThemeUseCase()

    fun getLocale(): String = getUserPreferLocaleUseCase()
}