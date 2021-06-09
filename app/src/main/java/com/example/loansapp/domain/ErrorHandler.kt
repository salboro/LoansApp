package com.example.loansapp.domain

import com.example.loansapp.domain.entity.ErrorType

interface ErrorHandler {

    fun getError(code: Int): ErrorType
}