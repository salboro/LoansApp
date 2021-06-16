package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.repository.UserInformationRepository
import javax.inject.Inject

class SetUserPreferThemeUseCase @Inject constructor(
    private val userInformationRepository: UserInformationRepository
) {

    operator fun invoke(themeType: ThemeType) {
        userInformationRepository.setTheme(themeType)
    }
}