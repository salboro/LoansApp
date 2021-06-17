package com.example.loansapp.di.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.RxWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.loansapp.domain.usecase.GetLoansUseCase
import com.example.loansapp.worker.NotifyWorker

class DaggerWorkerFactory(private val getLoansUseCase: GetLoansUseCase) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        val workerClass = Class.forName(workerClassName).asSubclass(RxWorker::class.java)
        val constructor =
            workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is NotifyWorker -> {
                instance.getLoansUseCase = getLoansUseCase
            }
        }
        return instance
    }

}