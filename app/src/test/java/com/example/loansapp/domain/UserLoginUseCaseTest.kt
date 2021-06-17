package com.example.loansapp.domain

import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.repository.AuthorizationRepository
import com.example.loansapp.domain.usecase.UserLoginUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserLoginUseCaseTest {
    private val name = "test"
    private val password = "test"
    private val successType = AuthorizeResultType.Success
    private val errorType = AuthorizeResultType.Error(ErrorType.Connection)

    private val authorizationRepository: AuthorizationRepository = mock()
    private val userLoginUseCase = UserLoginUseCase(authorizationRepository)

    @Test
    fun `WHEN userLoginUseCase(name, password) EXPECT success`() {
        whenever(authorizationRepository.login(name, password)).thenReturn(Single.just(successType))

        val success = userLoginUseCase(name, password)

        assertEquals(success, authorizationRepository.login(name, password))
    }

    @Test
    fun `WHEN userLoginUseCase(name, password) EXPECT error`() {
        whenever(authorizationRepository.login(name, password)).thenReturn(Single.just(errorType))

        val error = userLoginUseCase(name, password)

        assertEquals(error, authorizationRepository.login(name, password))
    }
}