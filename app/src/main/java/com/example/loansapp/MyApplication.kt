package com.example.loansapp

import android.app.Application
import com.example.loansapp.di.component.DaggerAppComponent

class MyApplication : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}