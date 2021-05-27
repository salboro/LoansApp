package com.example.fsnetworkhomework.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.fsnetworkhomework.di.viewModelFactory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelBuilderModule {
    @Binds
    fun bindViewModelFactory(
        factory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}