package com.example.loansapp.domain

import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.repository.AuthorizationRepository
import com.example.loansapp.domain.usecase.UserRegistrationUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserRegistrationUseCaseTest {
    private val name = "test"
    private val password = "test"
    private val successType = AuthorizeResultType.Success
    private val errorType = AuthorizeResultType.Error(ErrorType.Connection)

    private val authorizationRepository: AuthorizationRepository = mock()
    private val userRegistrationUseCase = UserRegistrationUseCase(authorizationRepository)

    @Test
    fun `WHEN userRegistrationUseCase(name, password) EXPECT successType`() {
        whenever(authorizationRepository.register(name, password)).thenReturn(
            Single.just(
                successType
            )
        )

        val success = userRegistrationUseCase(name, password)

        assertEquals(success, authorizationRepository.register(name, password))
    }

    @Test
    fun `WHEN userRegistrationUseCase(name, password) EXPECT errorType`() {
        whenever(
            authorizationRepository.register(
                name,
                password
            )
        ).thenReturn(Single.just(errorType))

        val error = userRegistrationUseCase(name, password)

        assertEquals(error, authorizationRepository.register(name, password))
    }
}