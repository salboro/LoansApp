package com.example.loansapp.domain

import com.example.loansapp.domain.repository.UserInformationRepository
import com.example.loansapp.domain.usecase.SetUserNameUseCase
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SetUserNameUseCaseTest {
    private val name = "test"

    private val userInformationRepository: UserInformationRepository = mock()
    private val setUserNameUseCase = SetUserNameUseCase(userInformationRepository)

    @Test
    fun `WHEN setUserNameUseCase(name) EXPECT just runs`() {
        Mockito.doNothing().whenever(userInformationRepository).setName(name)

        setUserNameUseCase(name)

        verify(userInformationRepository).setName(name)
    }
}