package com.example.fsnetworkhomework.di.component

import dagger.Component

@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}