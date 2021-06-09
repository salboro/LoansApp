package com.example.loansapp.data.datasource

import com.example.loansapp.data.ErrorHandlerImpl
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteLoansDataSource @Inject constructor(
    private val apiService: LoansApiService
) : LoansDataSource {

    companion object {
        const val USER_ALREADY_EXIST_RESPONSE_CODE = 400
        const val UNAUTHORIZED_RESPONSE_CODE = 401
        const val FORBIDDEN_RESPONSE_CODE = 402
        const val NOT_FOUND_RESPONSE_CODE = 403
    }

    override fun login(name: String, password: String): Single<ResultType<String>> {
        val mediaType = MediaType.parse("application/json")
        val headers = mapOf("accept" to "*/*", "Content-type" to "application/json")
        val body = RequestBody.create(mediaType, createBodyContentString(name, password))
        val response = apiService.login(headers, body)

        //Return beaver authorisation token or error code
        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                val s = it.errorBody()!!.string()
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    override fun register(name: String, password: String): Single<ResultType<RegistrationRequest>> {
        val mediaType = MediaType.parse("application/json")
        val headers = mapOf("accept" to "*/*", "Content-type" to "application/json")
        val body = RequestBody.create(mediaType, createBodyContentString(name, password))
        val response = apiService.registration(headers, body)

        //Return registerRequest or error code
        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    private fun createBodyContentString(name: String, password: String) = "{ \"name\": \"$name\", \"password\": \"$password\"}"
}