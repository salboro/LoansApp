package com.example.loansapp.presentation.enter.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.usecase.UserLoginUseCase
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> = _state

    fun login(name: String, password: String) {
        if (name.isNotEmpty() || password.isNotEmpty()) {
            _state.postValue(LoginViewState.Loading)

            loginUseCase(name, password)
                .subscribe(::onSuccess, ::onError)
                .untilDestroy()

        } else {
            sendError(ErrorType.InvalidData)
        }
    }

    private fun onSuccess(result: AuthorizeResultType) {
        when (result) {
            is AuthorizeResultType.Success -> _state.postValue(LoginViewState.SuccessAuthorised)
            is AuthorizeResultType.Error -> sendError(result.error)
        }
    }

    private fun onError(error: Throwable) {
        Log.i("login error", error.stackTraceToString())
        sendError(ErrorType.Connection)
    }

    private fun sendError(error: ErrorType) {
        _state.postValue(LoginViewState.Error(error))
    }
}