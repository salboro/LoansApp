package com.example.loansapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.usecase.*
import com.example.loansapp.presentation.loans.LoansConditionsViewState
import com.example.loansapp.presentation.loans.LoansViewModel
import com.example.loansapp.presentation.loans.LoansViewState
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class LoansViewModelTest {
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

    private val loansConditions = LoansConditions(
        percent = 10.0,
        period = 10,
        maxAmount = 10
    )

    private val loansConditionsStateObserver: Observer<LoansConditionsViewState> = mock()
    private val loansStateObserver: Observer<LoansViewState> = mock()

    private val errorType = ErrorType.Connection

    private val themeType = ThemeType.Light

    private val langCode = "en"

    private val getLoansUseCase: GetLoansUseCase = mock()
    private val getLoansConditionsUseCase: GetLoansConditionsUseCase = mock()
    private val setUserPreferLocaleUseCase: SetUserPreferLocaleUseCase = mock()
    private val setUserPreferThemeUseCase: SetUserPreferThemeUseCase = mock()
    private val addLoansToCacheUseCase: AddLoansToCacheUseCase = mock()
    private val getCachedLoansUseCase: GetCachedLoansUseCase = mock()

    private val loansViewModel = LoansViewModel(
        getLoansUseCase,
        getLoansConditionsUseCase,
        setUserPreferLocaleUseCase,
        setUserPreferThemeUseCase,
        addLoansToCacheUseCase,
        getCachedLoansUseCase
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `WHEN loading loans conditions returns with success EXPECT loans conditions state changes to success`() {
        whenever(getLoansConditionsUseCase()).thenReturn(
            Single.just(
                ResultType.Success(
                    loansConditions
                )
            )
        )
        loansViewModel.loansConditionsState.observeForever(loansConditionsStateObserver)
        loansViewModel.getLoansConditions()

        inOrder(loansConditionsStateObserver) {
            verify(loansConditionsStateObserver).onChanged(LoansConditionsViewState.Loading)
            verify(loansConditionsStateObserver).onChanged(
                LoansConditionsViewState.Success(
                    loansConditions
                )
            )
        }
    }

    @Test
    fun `WHEN loading loans conditions returns with error EXPECT loans conditions state changes to error`() {
        whenever(getLoansConditionsUseCase()).thenReturn(Single.just(ResultType.Error(errorType)))

        loansViewModel.loansConditionsState.observeForever(loansConditionsStateObserver)
        loansViewModel.getLoansConditions()

        inOrder(loansConditionsStateObserver) {
            verify(loansConditionsStateObserver).onChanged(LoansConditionsViewState.Loading)
            verify(loansConditionsStateObserver).onChanged(LoansConditionsViewState.Error(errorType))
        }
    }

    @Test
    fun `WHEN loading loans returns with success EXPECT loans state changes to success`() {
        whenever(getLoansUseCase()).thenReturn(Single.just(ResultType.Success(listOf(loan))))
        whenever(addLoansToCacheUseCase(listOf(loan))).thenReturn(Completable.complete())

        loansViewModel.loansState.observeForever(loansStateObserver)
        loansViewModel.getLoans()

        inOrder(loansStateObserver) {
            verify(loansStateObserver).onChanged(LoansViewState.Loading)
            verify(loansStateObserver).onChanged(LoansViewState.Success(listOf(loan)))
        }
    }

    @Test
    fun `WHEN loading loans returns with error EXPECT loans state changes to error`() {
        whenever(getLoansUseCase()).thenReturn(Single.just(ResultType.Error(errorType)))

        loansViewModel.loansState.observeForever(loansStateObserver)
        loansViewModel.getLoans()

        inOrder(loansStateObserver) {
            verify(loansStateObserver).onChanged(LoansViewState.Loading)
            verify(loansStateObserver).onChanged(LoansViewState.Error(errorType))
        }
    }

    @Test
    fun `WHEN loading loans returns with success EXPECT add loans to cache after this`() {
        whenever(getLoansUseCase()).thenReturn(Single.just(ResultType.Success(listOf(loan))))
        whenever(addLoansToCacheUseCase(listOf(loan))).thenReturn(Completable.complete())

        loansViewModel.getLoans()

        inOrder(getLoansUseCase, addLoansToCacheUseCase) {
            verify(getLoansUseCase).invoke()
            verify(addLoansToCacheUseCase).invoke(listOf(loan))
        }
    }

    @Test
    fun `WHEN set user theme EXPECT just runs`() {
        doNothing().whenever(setUserPreferThemeUseCase).invoke(themeType)

        loansViewModel.setUserTheme(themeType)

        verify(setUserPreferThemeUseCase).invoke(themeType)
    }

    @Test
    fun `WHEN set user locale EXPECT just runs`() {
        doNothing().whenever(setUserPreferLocaleUseCase).invoke(langCode)

        loansViewModel.setUserLocale(langCode)

        verify(setUserPreferLocaleUseCase).invoke(langCode)
    }

    @Test
    fun `WHEN try get cached loans success EXPECT loans state change to success cached`() {
        whenever(getCachedLoansUseCase()).thenReturn(Single.just(listOf(loan)))

        loansViewModel.loansState.observeForever(loansStateObserver)
        loansViewModel.tryGetCachedLoans()

        verify(loansStateObserver).onChanged(LoansViewState.CachedSuccess(listOf(loan)))
    }
}