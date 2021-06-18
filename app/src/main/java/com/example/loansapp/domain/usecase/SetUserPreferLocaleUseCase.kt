package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.repository.UserInformationRepository
import javax.inject.Inject

class SetUserPreferLocaleUseCase @Inject constructor(
    private val userInformationRepository: UserInformationRepository
) {

    operator fun invoke(langCode: String) {
        userInformationRepository.setLocale(langCode)
    }
}