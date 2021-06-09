package com.example.loansapp.data.network

import com.example.loansapp.di.converterFactory.ScalarsConverter
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoansApiService {

    @POST("login")
    @ScalarsConverter
    fun login(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Single<Response<String>>

    @POST("registration")
    fun registration(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Single<Response<RegistrationRequest>>


}