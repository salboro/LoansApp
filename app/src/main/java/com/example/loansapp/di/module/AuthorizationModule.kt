package com.example.loansapp.di.module

import com.example.loansapp.data.repository.AuthorizationRepositoryImpl
import com.example.loansapp.di.scope.FragmentScope
import com.example.loansapp.domain.repository.AuthorizationRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthorizationModule {

    @FragmentScope
    @Binds
    fun bindsLoansRepository(repository: AuthorizationRepositoryImpl): AuthorizationRepository
}