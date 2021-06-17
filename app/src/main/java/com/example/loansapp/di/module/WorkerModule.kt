package com.example.loansapp.di.module

import androidx.work.WorkerFactory
import com.example.loansapp.di.scope.AppScope
import com.example.loansapp.di.worker.DaggerWorkerFactory
import com.example.loansapp.domain.usecase.GetLoansUseCase
import dagger.Module
import dagger.Provides

@Module
class WorkerModule {

    @AppScope
    @Provides
    fun provideWorkerFactory(getLoansUseCase: GetLoansUseCase): WorkerFactory =
        DaggerWorkerFactory(getLoansUseCase)
}