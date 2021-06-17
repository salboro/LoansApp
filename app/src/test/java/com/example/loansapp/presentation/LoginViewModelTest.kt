package com.example.loansapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.usecase.CheckFirstLaunchUseCase
import com.example.loansapp.domain.usecase.SetUserNameUseCase
import com.example.loansapp.domain.usecase.UserLoginUseCase
import com.example.loansapp.presentation.authorization.login.LoginViewModel
import com.example.loansapp.presentation.authorization.login.LoginViewState
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginViewModelTest {
    private val name = "test"
    private val password = "test"
    private val errorType = ErrorType.Connection

    private val stateObserver: Observer<LoginViewState> = mock()

    private val loginUseCase: UserLoginUseCase = mock()
    private val setUserNameUseCase: SetUserNameUseCase = mock()
    private val checkFirstLaunchUseCase: CheckFirstLaunchUseCase = mock()

    private val loginViewModel =
        LoginViewModel(loginUseCase, setUserNameUseCase, checkFirstLaunchUseCase)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `WHEN login has valid data EXPECT state changes to loading`() {
        whenever(loginUseCase(name, password)).thenReturn(Single.just(AuthorizeResultType.Success))

        loginViewModel.state.observeForever(stateObserver)
        loginViewModel.login(name, password)

        verify(stateObserver).onChanged(LoginViewState.Loading)
    }

    @Test
    fun `WHEN login has invalid data EXPECT state changes to error`() {
        whenever(loginUseCase(name, password)).thenReturn(
            Single.just(
                AuthorizeResultType.Error(
                    errorType
                )
            )
        )

        loginViewModel.state.observeForever(stateObserver)
        loginViewModel.login(name, password)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(LoginViewState.Loading)
            verify(stateObserver).onChanged(LoginViewState.Error(errorType))
        }
    }

    @Test
    fun `WHEN check first launch EXPECT returns boolean`() {
        whenever(checkFirstLaunchUseCase()).thenReturn(true)

        val isFirstLaunch = loginViewModel.checkFirstLaunch()

        assertEquals(isFirstLaunch, checkFirstLaunchUseCase())
    }
}