package com.example.loansapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.usecase.AddLoansToCacheUseCase
import com.example.loansapp.domain.usecase.CreateLoanUseCase
import com.example.loansapp.presentation.createloan.CreateLoanViewModel
import com.example.loansapp.presentation.createloan.CreateLoanViewState
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CreateLoanViewModelTest {
    private val loan = Loan(
        id = 1,
        firstName = "test",
        lastName = "test",
        amount = 10.0,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
        date = "test",
        state = "APPROVED"
    )

    private val newLoan = NewLoan(
        firstName = "test",
        lastName = "test",
        amount = 10,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
    )

    private val errorType = ErrorType.Connection

    private val createLoanStateObserver: Observer<CreateLoanViewState> = mock()

    private val createLoanUseCase: CreateLoanUseCase = mock()
    private val addLoansToCacheUseCase: AddLoansToCacheUseCase = mock()

    private val createLoanViewModel = CreateLoanViewModel(
        createLoanUseCase,
        addLoansToCacheUseCase
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `WHEN creating new loan with success EXPECT change state to success`() {
        whenever(createLoanUseCase(newLoan)).thenReturn(Single.just(ResultType.Success(loan)))
        whenever(addLoansToCacheUseCase(listOf(loan))).thenReturn(Completable.complete())

        createLoanViewModel.state.observeForever(createLoanStateObserver)
        createLoanViewModel.createLoan(newLoan)

        inOrder(createLoanStateObserver) {
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Loading)
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Success(loan))
        }
    }

    @Test
    fun `WHEN creating new loan with error EXPECT change state to error`() {
        whenever(createLoanUseCase(newLoan)).thenReturn(Single.just(ResultType.Error(errorType)))

        createLoanViewModel.state.observeForever(createLoanStateObserver)
        createLoanViewModel.createLoan(newLoan)

        inOrder(createLoanStateObserver) {
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Loading)
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Error(errorType))
        }
    }

    @Test
    fun `WHEN creating new loan with success EXPECT add loan to cache after this`() {
        whenever(createLoanUseCase(newLoan)).thenReturn(Single.just(ResultType.Success(loan)))
        whenever(addLoansToCacheUseCase(listOf(loan))).thenReturn(Completable.complete())

        createLoanViewModel.state.observeForever(createLoanStateObserver)
        createLoanViewModel.createLoan(newLoan)

        inOrder(createLoanStateObserver) {
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Loading)
            verify(createLoanStateObserver).onChanged(CreateLoanViewState.Success(loan))
        }
    }
}