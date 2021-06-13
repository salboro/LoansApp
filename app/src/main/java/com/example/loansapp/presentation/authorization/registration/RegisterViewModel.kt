package com.example.loansapp.presentation.authorization.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.usecase.UserRegistrationUseCase
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registrationUseCase: UserRegistrationUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<RegistrationViewState>()
    val state: LiveData<RegistrationViewState> = _state

    fun register(name: String, password: String) {
        if (name.isNotEmpty() || password.isNotEmpty()) {
            _state.postValue(RegistrationViewState.Loading)

            registrationUseCase(name, password)
                .subscribe(::onSuccess, ::onError)
                .untilDestroy()

        } else {
            sendError(ErrorType.InvalidData)
        }
    }

    private fun onSuccess(result: AuthorizeResultType) {
        when (result) {
            is AuthorizeResultType.Success -> _state.postValue(RegistrationViewState.SuccessRegistered)
            is AuthorizeResultType.Error -> sendError(result.error)
        }
    }

    private fun onError(error: Throwable) {
        Log.i("register error", error.stackTraceToString())
        sendError(ErrorType.Connection)
    }

    private fun sendError(error: ErrorType) {
        _state.postValue(RegistrationViewState.Error(error))
    }
}