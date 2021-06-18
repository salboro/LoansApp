package com.example.loansapp.data.datasource

import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.data.network.RegistrationRequest
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteDataSourceTest {
    private val loan = Loan(
        id = 1,
        firstName = "test",
        lastName = "test",
        amount = 10.0,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
        date = "test",
        state = "APPROVED"
    )

    private val newLoan = NewLoan(
        firstName = "test",
        lastName = "test",
        amount = 10,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
    )

    private val token = "test"
    private val name = "test"
    private val password = "test"
    private val responseBody = "test"
    private val registrationRequest = RegistrationRequest("test", "test")
    private val loanConditions = LoansConditions(10.0, 10, 10)

    private val apiService: LoansApiService = mock()
    private val remoteDataSource = RemoteDataSourceImpl(apiService)

    @Test
    fun `WHEN login success EXPRECT returns success`() {
        whenever(apiService.login(any(), any())).thenReturn(
            Single.just(
                Response.success(
                    responseBody
                )
            )
        )

        val observer: SingleObserver<ResultType<String>> = mock()

        remoteDataSource.login(name, password).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(responseBody))
    }

    @Test
    fun `WHEN register success EXPECT returns success`() {
        whenever(apiService.registration(any(), any())).thenReturn(
            Single.just(
                Response.success(
                    registrationRequest
                )
            )
        )

        val observer: SingleObserver<ResultType<RegistrationRequest>> = mock()
        remoteDataSource.register(name, password).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(registrationRequest))
    }

    @Test
    fun `WHEN get loans success EXPECT returns success`() {
        whenever(apiService.getLoans(any())).thenReturn(Single.just(Response.success(listOf(loan))))

        val observer: SingleObserver<ResultType<List<Loan>>> = mock()
        remoteDataSource.getLoans(token).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(listOf(loan)))
    }

    @Test
    fun `WHEN get loans conditions success EXPECT returns success`() {
        whenever(apiService.getLoansConditions(any())).thenReturn(
            Single.just(
                Response.success(
                    loanConditions
                )
            )
        )

        val observer: SingleObserver<ResultType<LoansConditions>> = mock()
        remoteDataSource.getLoansConditions(token).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(loanConditions))
    }

    @Test
    fun `WHEN create new loan success EXPECT return loan`() {
        whenever(
            apiService.createLoan(
                any(),
                any()
            )
        ).thenReturn(Single.just(Response.success(loan)))

        val observer: SingleObserver<ResultType<Loan>> = mock()
        remoteDataSource.createLoan(newLoan, token).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(loan))
    }
}