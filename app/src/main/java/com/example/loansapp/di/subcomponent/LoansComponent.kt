package com.example.loansapp.di.subcomponent

import com.example.loansapp.di.module.LoansModule
import com.example.loansapp.di.scope.FragmentScope
import com.example.loansapp.ui.loans.LoansFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LoansModule::class])
interface LoansComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoansComponent
    }

    fun inject(fragment: LoansFragment)
}