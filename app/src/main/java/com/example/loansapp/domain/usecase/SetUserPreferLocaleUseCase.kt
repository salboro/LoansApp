package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetUserPreferLocaleUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(langCode: String) {
        userPreferencesRepository.setLocale(langCode)
    }
}