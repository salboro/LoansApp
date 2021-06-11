package com.example.loansapp.di.module

import com.example.loansapp.di.subcomponent.AuthorizationComponent
import com.example.loansapp.di.subcomponent.LoansComponent
import dagger.Module

@Module(subcomponents = [AuthorizationComponent::class, LoansComponent::class])
class AppSubcomponents