package com.example.loansapp.presentation.authorization

import androidx.lifecycle.ViewModel
import com.example.loansapp.domain.usecase.SetUserPreferLocaleUseCase
import javax.inject.Inject

class EnterViewModel @Inject constructor(
    private val setUserPreferLocaleUseCase: SetUserPreferLocaleUseCase
) : ViewModel() {

    fun setUserLocale(langCode: String) {
        setUserPreferLocaleUseCase(langCode)
    }
}