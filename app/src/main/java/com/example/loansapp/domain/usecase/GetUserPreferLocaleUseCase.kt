package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferLocaleUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(): String =
        userPreferencesRepository.getLocale()
}