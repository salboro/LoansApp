package com.example.loansapp.data.network

import com.example.loansapp.di.converter.MoshiConverter
import com.example.loansapp.di.converter.ScalarsConverter
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface LoansApiService {

    @POST("login")
    @ScalarsConverter
    fun login(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Single<Response<String>>

    @POST("registration")
    @MoshiConverter
    fun registration(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Single<Response<RegistrationRequest>>


    @GET("loans/all")
    @MoshiConverter
    fun getLoans(
        @HeaderMap headers: Map<String, String>
    ): Single<Response<List<Loan>>>

    @GET("loans/conditions")
    @MoshiConverter
    fun getLoansConditions(
        @HeaderMap headers: Map<String, String>
    ): Single<Response<LoansConditions>>

    @POST("loans")
    @MoshiConverter
    fun createLoan(
        @HeaderMap headers: Map<String, String>,
        @Body requestBody: RequestBody
    ): Single<Response<Loan>>
}