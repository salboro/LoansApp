package com.example.loansapp.di.module

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.LocalDataSourceImpl
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.data.datasource.RemoteDataSourceImpl
import com.example.loansapp.di.scope.AppScope
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

    @AppScope
    @Binds
    fun bindsRemoteDataSource(dataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @AppScope
    @Binds
    fun bindsLocalDataSource(dataSourceImpl: LocalDataSourceImpl): LocalDataSource
}