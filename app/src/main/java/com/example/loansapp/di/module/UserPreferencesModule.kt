package com.example.loansapp.di.module

import com.example.loansapp.data.repository.UserPreferencesRepositoryImpl
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module

@Module
interface UserPreferencesModule {

    @AppScope
    @Binds
    fun bindsUserPreferencesRepository(repositoryImpl: UserPreferencesRepositoryImpl): UserPreferencesRepository
}