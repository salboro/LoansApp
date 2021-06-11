package com.example.loansapp.di.component

import android.content.Context
import com.example.loansapp.di.module.AppModule
import com.example.loansapp.di.module.AppSubcomponents
import com.example.loansapp.di.module.DataModule
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.di.subcomponent.AuthorizationComponent
import com.example.loansapp.di.subcomponent.LoansComponent
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, DataModule::class, AppSubcomponents::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun authorizationComponent(): AuthorizationComponent.Factory

    fun loansComponent(): LoansComponent.Factory
}