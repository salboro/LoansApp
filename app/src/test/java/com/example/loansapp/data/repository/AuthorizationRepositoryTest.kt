package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.rule.RxImmediateSchedulerRule
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class AuthorizationRepositoryTest {
    private val name = "test"
    private val password = "test"
    private val errorType = ErrorType.Connection
    private val authorizationToken = "test"

    private val observer: SingleObserver<AuthorizeResultType> = mock()

    private val remoteDataSource: RemoteDataSource = mock()
    private val localDataSource: LocalDataSource = mock()
    private val authorizationRepository =
        AuthorizationRepositoryImpl(remoteDataSource, localDataSource)

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Test
    fun `WHEN login success EXPECT returns success`() {
        whenever(remoteDataSource.login(name, password)).thenReturn(
            Single.just(
                ResultType.Success(
                    authorizationToken
                )
            )
        )

        doNothing().whenever(localDataSource).saveBearerToken(authorizationToken)

        authorizationRepository.login(name, password).subscribeWith(observer)

        verify(observer).onSuccess(AuthorizeResultType.Success)
    }

    @Test
    fun `WHEN login error EXPECT returns error`() {
        whenever(remoteDataSource.login(name, password)).thenReturn(
            Single.just(
                ResultType.Error(
                    errorType
                )
            )
        )

        doNothing().whenever(localDataSource).saveBearerToken(authorizationToken)

        authorizationRepository.login(name, password).subscribeWith(observer)

        verify(observer).onSuccess(AuthorizeResultType.Error(errorType))
    }

    @Test
    fun `WHEN login success EXPECT save bearer token`() {
        whenever(remoteDataSource.login(name, password)).thenReturn(
            Single.just(
                ResultType.Success(
                    authorizationToken
                )
            )
        )

        doNothing().whenever(localDataSource).saveBearerToken(authorizationToken)

        authorizationRepository.login(name, password).subscribeWith(observer)

        inOrder(
            remoteDataSource,
            localDataSource
        ) {
            verify(remoteDataSource).login(name, password)
            verify(localDataSource).saveBearerToken(authorizationToken)
        }
    }
}