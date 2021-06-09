package com.example.loansapp.di.component

import android.content.Context
import com.example.loansapp.di.module.AppModule
import com.example.loansapp.di.module.DataModule
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.ui.enter.LoginFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: LoginFragment)
}