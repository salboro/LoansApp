package com.example.loansapp.di.subcomponent

import com.example.loansapp.ui.createloan.CreateLoanFragment
import com.example.loansapp.ui.loans.LoansFragment
import dagger.Subcomponent

@Subcomponent()
interface LoansComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoansComponent
    }

    fun inject(fragment: LoansFragment)
    fun inject(fragment: CreateLoanFragment)
}