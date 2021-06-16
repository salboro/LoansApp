package com.example.loansapp.presentation.loans

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.data.network.Loan
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

        getLoansConditionsUseCase().subscribe({ result ->
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
        }, {
            Log.i("msg1", it.stackTraceToString())

            _loansConditionsState.postValue(
                LoansConditionsViewState.Error(
                    ErrorType.Connection
                )
            )
        }).untilDestroy()
    }

    fun getLoans() {
        _loansState.postValue(LoansViewState.Loading)

        getLoansUseCase()
            .doAfterSuccess { result ->
                if (result is ResultType.Success) {
                    addLoansToCache(result.data)
                }
            }
            .subscribe({ result ->
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
            }, {
                Log.i("msg2", it.stackTraceToString())

                _loansState.postValue(
                    LoansViewState.Error(
                        ErrorType.Connection
                    )
                )
            }).untilDestroy()
    }

    fun setUserTheme(themeType: ThemeType) {
        setUserPreferThemeUseCase(themeType)
    }

    fun setUserLocale(langCode: String) {
        setUserPreferLocaleUseCase(langCode)
    }

    private fun addLoansToCache(loans: List<Loan>) {
        addLoansToCacheUseCase(loans).subscribe().untilDestroy()
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
}