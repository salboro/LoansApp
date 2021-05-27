package com.example.fsnetworkhomework

import android.app.Application
import com.example.fsnetworkhomework.di.component.DaggerAppComponent

class MyApplication : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}