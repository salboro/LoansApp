package com.example.loansapp.di.subcomponent

import com.example.loansapp.ui.MainActivity
import dagger.Subcomponent

@Subcomponent
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}