package com.example.loansapp.di.module

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.data.network.RegistrationRequest
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate

class LoansApiMockService(
    private val delegate: BehaviorDelegate<LoansApiService>
) : LoansApiService {
    private val loans: List<Loan> = listOf(
        Loan(
            id = 1,
            firstName = "test",
            lastName = "test",
            amount = 10.0,
            percent = 10.0,
            phoneNumber = "123",
            period = 45,
            date = "test",
            state = "APPROVED"
        ),
        Loan(
            id = 2,
            firstName = "test",
            lastName = "test",
            amount = 10.0,
            percent = 10.0,
            phoneNumber = "123",
            period = 45,
            date = "test",
            state = "APPROVED"
        ),
        Loan(
            id = 3,
            firstName = "test",
            lastName = "test",
            amount = 10.0,
            percent = 10.0,
            phoneNumber = "123",
            period = 45,
            date = "test",
            state = "REJECTED"
        )
    )


    override fun login(
        headers: Map<String, String>,
        requestBody: RequestBody
    ): Single<Response<String>> =
        delegate.returningResponse("test").login(headers, requestBody)

    override fun registration(
        headers: Map<String, String>,
        requestBody: RequestBody
    ): Single<Response<RegistrationRequest>> =
        delegate.returningResponse(RegistrationRequest("test", "test"))
            .registration(headers, requestBody)

    override fun getLoans(headers: Map<String, String>): Single<Response<List<Loan>>> =
        delegate.returningResponse(loans).getLoans(headers)

    override fun getLoansConditions(headers: Map<String, String>): Single<Response<LoansConditions>> =
        delegate.returningResponse(LoansConditions(10.0, 15, 10000)).getLoansConditions(headers)

    override fun createLoan(
        headers: Map<String, String>,
        requestBody: RequestBody
    ): Single<Response<Loan>> =
        delegate.returningResponse(loans.first()).createLoan(headers, requestBody)
}