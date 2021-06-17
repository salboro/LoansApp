package com.example.loansapp.domain

import com.example.loansapp.domain.repository.UserInformationRepository
import com.example.loansapp.domain.usecase.SetUserPreferLocaleUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SetPreferLocaleUseCaseTest {
    private val langCode = "en"

    private val userInformationRepository: UserInformationRepository = mock()
    private val setPreferLocaleUseCase = SetUserPreferLocaleUseCase(userInformationRepository)

    @Test
    fun `WHEN setPreferLocaleUseCase(langCode) EXPECT just run`() {
        Mockito.doNothing().whenever(userInformationRepository).setLocale(langCode)

        setPreferLocaleUseCase(langCode)

        verify(userInformationRepository).setLocale(langCode)
    }
}