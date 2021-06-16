package com.example.loansapp.domain.usecase

import com.example.loansapp.domain.repository.UserInformationRepository
import javax.inject.Inject

class CheckFirstLaunchUseCase @Inject constructor(
    private val userInformationRepository: UserInformationRepository
) {

    operator fun invoke(): Boolean = userInformationRepository.checkFirstLaunch()
}