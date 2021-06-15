package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferThemeUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(): ThemeType =
        userPreferencesRepository.getTheme()
}