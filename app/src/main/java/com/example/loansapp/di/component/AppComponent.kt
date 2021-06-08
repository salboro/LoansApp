package com.example.loansapp.di.component

import dagger.Component

@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}