package com.example.loansapp.presentation.loans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.entity.ThemeType
import com.example.loansapp.domain.usecase.*
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getLoansUseCase: GetLoansUseCase,
    private val getLoansConditionsUseCase: GetLoansConditionsUseCase,
    private val setUserPreferLocaleUseCase: SetUserPreferLocaleUseCase,
    private val setUserPreferThemeUseCase: SetUserPreferThemeUseCase,
    private val addLoansToCacheUseCase: AddLoansToCacheUseCase,
    private val getCachedLoansUseCase: GetCachedLoansUseCase
) : BaseViewModel() {

    private val _loansState = MutableLiveData<LoansViewState>()
    val loansState: LiveData<LoansViewState> = _loansState

    private val _loansConditionsState = MutableLiveData<LoansConditionsViewState>()
    val loansConditionsState: LiveData<LoansConditionsViewState> = _loansConditionsState

    fun getLoansConditions() {
        _loansConditionsState.postValue(LoansConditionsViewState.Loading)

        getLoansConditionsUseCase().subscribe(::onGetLoansConditionsSuccess) {
            onGetLoansConditionsError()
        }.untilDestroy()
    }

    private fun onGetLoansConditionsError() {
        _loansConditionsState.postValue(
            LoansConditionsViewState.Error(
                ErrorType.Connection
            )
        )
    }

    private fun onGetLoansConditionsSuccess(result: ResultType<LoansConditions>) {
        when (result) {
            is ResultType.Success -> _loansConditionsState.postValue(
                LoansConditionsViewState.Success(
                    result.data
                )
            )

            is ResultType.Error -> _loansConditionsState.postValue(
                LoansConditionsViewState.Error(
                    result.error
                )
            )
        }
    }

    fun getLoans() {
        _loansState.postValue(LoansViewState.Loading)

        getLoansUseCase()
            .doAfterSuccess(::doAfterSuccessGetLoans)
            .subscribe(::onGetLoansSuccess) {
                onGetLoansError()
            }.untilDestroy()
    }

    private fun doAfterSuccessGetLoans(result: ResultType<List<Loan>>?) {
        if (result is ResultType.Success) {
            addLoansToCache(result.data)
        }
    }

    private fun onGetLoansError() {
        _loansState.postValue(
            LoansViewState.Error(
                ErrorType.Connection
            )
        )
    }

    private fun onGetLoansSuccess(result: ResultType<List<Loan>>?) {
        when (result) {
            is ResultType.Success -> {
                _loansState.postValue(
                    LoansViewState.Success(
                        result.data
                    )
                )
            }

            is ResultType.Error -> _loansState.postValue(
                LoansViewState.Error(
                    result.error
                )
            )
        }
    }

    fun setUserTheme(themeType: ThemeType) {
        setUserPreferThemeUseCase(themeType)
    }

    fun setUserLocale(langCode: String) {
        setUserPreferLocaleUseCase(langCode)
    }

    fun tryGetCachedLoans() {
        getCachedLoansUseCase().subscribe({ loans ->
            if (loans.isNotEmpty()) {
                _loansState.postValue(LoansViewState.CachedSuccess(loans))
            }
        }, {
            throw it
        }).untilDestroy()
    }

    private fun addLoansToCache(loans: List<Loan>) {
        addLoansToCacheUseCase(loans).subscribe().untilDestroy()
    }
}