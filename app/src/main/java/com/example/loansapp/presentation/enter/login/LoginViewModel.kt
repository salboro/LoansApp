package com.example.loansapp.presentation.enter.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.usecase.UserLoginUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: UserLoginUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<LoginViewState>(LoginViewState.Default)
    val state: LiveData<LoginViewState> = _state

    fun login(name: String, password: String) {
        Log.i("call", "viewModel")
        loginUseCase(name,password).subscribe({
            Log.i("call", "succ")
            when (it) {
                is ResultType.Success -> _state.postValue(LoginViewState.SuccessAuthorised(it.data))
                is ResultType.Error -> _state.postValue(LoginViewState.Error(it.error))
            }

        }, {
            Log.i("call", it.stackTraceToString())
        }).addTo(compositeDisposable)
    }
}