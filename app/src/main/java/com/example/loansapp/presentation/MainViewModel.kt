package com.example.loansapp.presentation

import androidx.lifecycle.ViewModel
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.usecase.GetUserPreferThemeUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserPreferThemeUseCase: GetUserPreferThemeUseCase
) : ViewModel() {

    fun getTheme(): ThemeType = getUserPreferThemeUseCase()
}