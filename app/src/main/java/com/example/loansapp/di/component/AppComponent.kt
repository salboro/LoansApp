package com.example.loansapp.di.component

import android.content.Context
import com.example.loansapp.MyApplication
import com.example.loansapp.di.module.*
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.di.subcomponent.AuthorizationComponent
import com.example.loansapp.di.subcomponent.LoansComponent
import com.example.loansapp.di.subcomponent.MainComponent
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules =
    [
        AppModule::class,
        DataModule::class,
        UserPreferencesModule::class,
        WorkerModule::class,
        LoansModule::class,
        AppSubcomponents::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun authorizationComponent(): AuthorizationComponent.Factory

    fun loansComponent(): LoansComponent.Factory

    fun mainComponent(): MainComponent.Factory

    fun inject(application: MyApplication)
}