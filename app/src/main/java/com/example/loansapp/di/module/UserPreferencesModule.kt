package com.example.loansapp.di.module

import com.example.loansapp.data.repository.UserInformationRepositoryImpl
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.domain.repository.UserInformationRepository
import dagger.Binds
import dagger.Module

@Module
interface UserPreferencesModule {

    @AppScope
    @Binds
    fun bindsUserPreferencesRepository(repositoryImpl: UserInformationRepositoryImpl): UserInformationRepository
}