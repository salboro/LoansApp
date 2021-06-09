package com.example.loansapp.di.module

import com.example.loansapp.data.LoansRepositoryImpl
import com.example.loansapp.data.datasource.LoansDataSource
import com.example.loansapp.data.datasource.RemoteLoansDataSource
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.domain.LoansRepository
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @AppScope
    @Binds
    fun bindsRemoteDataSource(dataSource: RemoteLoansDataSource): LoansDataSource

    @AppScope
    @Binds
    fun bindsLoansRepository(repository: LoansRepositoryImpl): LoansRepository
}