package com.example.loansapp.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun Disposable.untilDestroy() {
        compositeDisposable.add(this)
    }
}