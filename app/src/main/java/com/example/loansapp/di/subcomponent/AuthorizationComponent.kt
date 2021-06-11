package com.example.loansapp.di.subcomponent

import com.example.loansapp.di.module.AuthorizationModule
import com.example.loansapp.di.scope.FragmentScope
import com.example.loansapp.ui.enter.LoginFragment
import com.example.loansapp.ui.enter.RegistrationFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [AuthorizationModule::class])
interface AuthorizationComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthorizationComponent
    }

    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
}