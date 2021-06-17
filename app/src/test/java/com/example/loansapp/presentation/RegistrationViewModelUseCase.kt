package com.example.loansapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.usecase.UserRegistrationUseCase
import com.example.loansapp.presentation.authorization.registration.RegistrationViewModel
import com.example.loansapp.presentation.authorization.registration.RegistrationViewState
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RegistrationViewModelUseCase {
    private val name = "test"
    private val password = "test"
    private val errorType = ErrorType.Connection

    private val stateObserver: Observer<RegistrationViewState> = mock()

    private val registrationUseCase: UserRegistrationUseCase = mock()
    private val registrationViewModel = RegistrationViewModel(registrationUseCase)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `WHEN registration is success EXPECT state changes to success`() {
        whenever(
            registrationUseCase(
                name,
                password
            )
        ).thenReturn(Single.just(AuthorizeResultType.Success))

        registrationViewModel.state.observeForever(stateObserver)
        registrationViewModel.register(name, password)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(RegistrationViewState.Loading)
            verify(stateObserver).onChanged(RegistrationViewState.SuccessRegistered)
        }
    }

    @Test
    fun `WHEN registration is error EXPECT state changes to error`() {
        whenever(
            registrationUseCase(
                name,
                password
            )
        ).thenReturn(Single.just(AuthorizeResultType.Error(errorType)))

        registrationViewModel.state.observeForever(stateObserver)
        registrationViewModel.register(name, password)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(RegistrationViewState.Loading)
            verify(stateObserver).onChanged(RegistrationViewState.Error(errorType))
        }
    }
}