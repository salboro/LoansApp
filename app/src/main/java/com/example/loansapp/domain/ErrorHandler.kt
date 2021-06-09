package com.example.loansapp.domain

import com.example.loansapp.domain.entity.ErrorEntity

interface ErrorHandler {

    fun getError(code: Int): ErrorEntity
}