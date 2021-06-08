package com.example.loansapp.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.loansapp.di.viewModelFactory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelBuilderModule {
    @Binds
    fun bindViewModelFactory(
        factory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}