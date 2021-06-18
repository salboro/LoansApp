package com.example.loansapp.data.datasource

import com.example.loansapp.data.ErrorHandlerImpl
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: LoansApiService
) : RemoteDataSource {

    override fun login(name: String, password: String): Single<ResultType<String>> {
        val mediaType = MediaType.parse("application/json")
        val headers = mapOf("accept" to "*/*", "Content-type" to "application/json")
        val body =
            RequestBody.create(mediaType, createAuthorizationBodyContentString(name, password))
        val response = apiService.login(headers, body)

        //Return beaver authorisation token or error entity
        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    override fun register(name: String, password: String): Single<ResultType<RegistrationRequest>> {
        val mediaType = MediaType.parse("application/json")
        val headers = mapOf("accept" to "*/*", "Content-type" to "application/json")
        val body =
            RequestBody.create(mediaType, createAuthorizationBodyContentString(name, password))
        val response = apiService.registration(headers, body)

        //Return registerRequest or error entity
        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    override fun getLoans(authorizationToken: String): Single<ResultType<List<Loan>>> {
        val headers = mapOf("accept" to "*/*", "authorization" to authorizationToken)
        val response = apiService.getLoans(headers)

        //Return loans list or error entity
        return response.map {
            if (it.isSuccessful) {
                it.body()?.let { list ->
                    ResultType.Success(list)
                }
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    override fun getLoansConditions(authorizationToken: String): Single<ResultType<LoansConditions>> {
        val headers = mapOf("accept" to "*/*", "authorization" to authorizationToken)
        val response = apiService.getLoansConditions(headers)

        //Return loans conditions or error entity
        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }


    override fun createLoan(
        newLoan: NewLoan,
        authorizationToken: String
    ): Single<ResultType<Loan>> {
        val mediaType = MediaType.parse("application/json")
        val headers = mapOf(
            "accept" to "*/*",
            "Content-type" to "application/json",
            "authorization" to authorizationToken
        )
        val body =
            RequestBody.create(
                mediaType,
                createLoanCreationBodyContentString(newLoan)
            )

        val response = apiService.createLoan(headers, body)


        return response.map {
            if (it.isSuccessful) {
                ResultType.Success(it.body()!!)
            } else {
                ResultType.Error(ErrorHandlerImpl().getError(it.code()))
            }
        }
    }

    private fun createAuthorizationBodyContentString(name: String, password: String) =
        "{ \"name\": \"$name\", \"password\": \"$password\"}"

    private fun createLoanCreationBodyContentString(
        newLoan: NewLoan
    ) = "{ \"amount\": ${newLoan.amount}," +
            " \"firstName\": \"${newLoan.firstName}\"," +
            " \"lastName\": \"${newLoan.lastName}\"," +
            " \"percent\": ${newLoan.percent}," +
            " \"period\": ${newLoan.period}," +
            " \"phoneNumber\": \"${newLoan.phoneNumber}\"}"

}