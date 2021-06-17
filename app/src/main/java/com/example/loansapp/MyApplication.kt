package com.example.loansapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.loansapp.di.component.DaggerAppComponent
import javax.inject.Inject

class MyApplication : Application() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        configureWorkManager()
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }
}