package com.example.loansapp.di.module

import com.example.loansapp.data.repository.LoansRepositoryImpl
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.domain.repository.LoansRepository
import dagger.Binds
import dagger.Module

@Module
interface LoansModule {

    @AppScope
    @Binds
    fun bindsLoansRepository(repository: LoansRepositoryImpl): LoansRepository
}