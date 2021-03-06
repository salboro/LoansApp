package com.example.loansapp.presentation.authorization.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.loansapp.domain.entity.AuthorizeResultType
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.usecase.CheckFirstLaunchUseCase
import com.example.loansapp.domain.usecase.SetUserNameUseCase
import com.example.loansapp.domain.usecase.UserLoginUseCase
import com.example.loansapp.presentation.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase,
    private val setUserNameUseCase: SetUserNameUseCase,
    private val checkFirstLaunchUseCase: CheckFirstLaunchUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> = _state

    private var userName = ""

    fun login(name: String, password: String) {
        if (name.isNotEmpty() || password.isNotEmpty()) {
            _state.postValue(LoginViewState.Loading)
            userName = name

            loginUseCase(name, password)
                .subscribe(::onSuccess) {
                    onError()
                }
                .untilDestroy()

        } else {
            sendError(ErrorType.InvalidData)
        }
    }

    fun checkFirstLaunch(): Boolean = checkFirstLaunchUseCase()

    private fun onSuccess(result: AuthorizeResultType) {
        when (result) {
            is AuthorizeResultType.Success -> {
                _state.postValue(LoginViewState.SuccessAuthorised)
                setUserNameUseCase(userName)
            }

            is AuthorizeResultType.Error -> sendError(result.error)
        }
    }

    private fun onError() {
        sendError(ErrorType.Connection)
    }

    private fun sendError(error: ErrorType) {
        _state.postValue(LoginViewState.Error(error))
    }
}