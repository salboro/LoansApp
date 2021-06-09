package com.example.loansapp.data

import com.example.loansapp.domain.ErrorHandler
import com.example.loansapp.domain.entity.ErrorType

class ErrorHandlerImpl : ErrorHandler {

    private companion object {
        const val UNSATISFIABLE_RESPONSE_CODE = 400
        const val UNAUTHORIZED_RESPONSE_CODE = 401
        const val FORBIDDEN_RESPONSE_CODE = 402
        const val NOT_FOUND_RESPONSE_CODE = 403
    }

    override fun getError(code: Int): ErrorType {
        return when (code) {
            UNSATISFIABLE_RESPONSE_CODE -> ErrorType.Network

            UNAUTHORIZED_RESPONSE_CODE -> ErrorType.Unauthorized

            FORBIDDEN_RESPONSE_CODE -> ErrorType.AccessDenied

            NOT_FOUND_RESPONSE_CODE -> ErrorType.NotFound

            else -> ErrorType.Unknown
        }
    }
}