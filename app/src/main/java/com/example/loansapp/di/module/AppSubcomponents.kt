package com.example.loansapp.di.module

import com.example.loansapp.di.subcomponent.AuthorizationComponent
import dagger.Module

@Module(subcomponents = [AuthorizationComponent::class])
class AppSubcomponents