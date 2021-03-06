package com.example.loansapp.presentation.createloan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.usecase.AddLoansToCacheUseCase
import com.example.loansapp.domain.usecase.CreateLoanUseCase
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class CreateLoanViewModel @Inject constructor(
    private val createLoanUseCase: CreateLoanUseCase,
    private val addLoansToCacheUseCase: AddLoansToCacheUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<CreateLoanViewState>()
    val state: LiveData<CreateLoanViewState> = _state

    fun createLoan(newLoan: NewLoan) {
        if (checkNewLoan(newLoan)) {
            _state.postValue(CreateLoanViewState.Loading)
            createLoanUseCase(newLoan)
                .doAfterSuccess(::doAfterCreateLoanSuccess)
                .subscribe(::onCreateLoanSuccess) {
                    sendError(ErrorType.Connection)
                }.untilDestroy()

        } else {
            sendError(ErrorType.InvalidData)
        }

    }

    private fun doAfterCreateLoanSuccess(result: ResultType<Loan>?) {
        if (result is ResultType.Success) {
            addNewLoanToCache(result.data)
        }
    }

    private fun onCreateLoanSuccess(result: ResultType<Loan>?) {
        when (result) {
            is ResultType.Success -> _state.postValue(
                CreateLoanViewState.Success(result.data)
            )

            is ResultType.Error -> sendError(result.error)
        }
    }

    private fun checkNewLoan(newLoan: NewLoan): Boolean {
        return newLoan.firstName.isNotEmpty() &&
                newLoan.lastName.isNotEmpty() &&
                newLoan.phoneNumber.isNotEmpty()
    }

    private fun addNewLoanToCache(loan: Loan) {
        addLoansToCacheUseCase(listOf(loan)).subscribe().untilDestroy()
    }

    private fun sendError(error: ErrorType) {
        _state.postValue(CreateLoanViewState.Error(error))
    }
}