package com.example.loansapp.domain

import com.example.loansapp.domain.repository.UserInformationRepository
import com.example.loansapp.domain.usecase.CheckFirstLaunchUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckFirstLaunchUseCaseTest {

    private val userInformationRepository: UserInformationRepository = mock()
    private val checkFirstLaunchUseCase = CheckFirstLaunchUseCase(userInformationRepository)

    @Test
    fun `WHEN checkFirstLaunchUseCase() EXPECT Boolean`() {
        whenever(userInformationRepository.checkFirstLaunch()).thenReturn(true)

        val isFirstLaunch = checkFirstLaunchUseCase()

        assertEquals(isFirstLaunch, userInformationRepository.checkFirstLaunch())
    }
}