package com.example.loansapp.presentation

import com.example.loansapp.domain.usecase.SetUserPreferLocaleUseCase
import com.example.loansapp.presentation.authorization.EnterViewModel
import org.junit.Test
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class EnterViewModelTest {
    private val langCode = "en"

    private val setUserPreferLocaleUseCase: SetUserPreferLocaleUseCase = mock()
    private val enterViewModel = EnterViewModel(setUserPreferLocaleUseCase)

    @Test
    fun `WHEN set locale EXPECT just runs`() {
        doNothing().whenever(setUserPreferLocaleUseCase).invoke(langCode)

        enterViewModel.setUserLocale(langCode)

        verify(setUserPreferLocaleUseCase).invoke(langCode)
    }

}