package com.example.loansapp.data.repository

import com.example.loansapp.data.datasource.LocalDataSource
import com.example.loansapp.data.datasource.RemoteDataSource
import com.example.loansapp.data.network.Loan
import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ErrorType
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.rule.RxImmediateSchedulerRule
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoansRepositoryTest {
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

    private val loansConditions = LoansConditions(
        percent = 10.0,
        period = 10,
        maxAmount = 10
    )

    private val errorType = ErrorType.Connection

    private val authorizationToken = "test"

    private val localDataSource: LocalDataSource = mock()
    private val remoteDataSource: RemoteDataSource = mock()

    private val loansRepository = LoansRepositoryImpl(localDataSource, remoteDataSource)

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Test
    fun `WHEN get all loans success EXPECT returns success`() {
        whenever(remoteDataSource.getLoans(authorizationToken)).thenReturn(
            Single.just(
                ResultType.Success(
                    listOf(loan)
                )
            )
        )

        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<List<Loan>>> = mock()

        loansRepository.getAll().subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(listOf(loan)))
    }

    @Test
    fun `WHEN get all loans error EXPECT returns error`() {
        whenever(remoteDataSource.getLoans(authorizationToken)).thenReturn(
            Single.just(
                ResultType.Error(errorType)
            )
        )

        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<List<Loan>>> = mock()

        loansRepository.getAll().subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Error(errorType))
    }


    @Test
    fun `WHEN get conditions success EXPECT returns success`() {
        whenever(remoteDataSource.getLoansConditions(authorizationToken)).thenReturn(
            Single.just(
                ResultType.Success(loansConditions)
            )
        )
        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<LoansConditions>> = mock()

        loansRepository.getConditions().subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(loansConditions))
    }

    @Test
    fun `WHEN get conditions error EXPECT returns error`() {
        whenever(remoteDataSource.getLoansConditions(authorizationToken)).thenReturn(
            Single.just(
                ResultType.Error(errorType)
            )
        )
        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<LoansConditions>> = mock()

        loansRepository.getConditions().subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Error(errorType))
    }

    @Test
    fun `WHEN create loan success EXPECT returns success`() {
        whenever(remoteDataSource.createLoan(newLoan, authorizationToken)).thenReturn(
            Single.just(
                ResultType.Success(loan)
            )
        )

        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<Loan>> = mock()

        loansRepository.create(newLoan).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Success(loan))
    }

    @Test
    fun `WHEN create loan error EXPECT returns error`() {
        whenever(remoteDataSource.createLoan(newLoan, authorizationToken)).thenReturn(
            Single.just(
                ResultType.Error(errorType)
            )
        )

        whenever(localDataSource.getBearerToken()).thenReturn(authorizationToken)

        val observer: SingleObserver<ResultType<Loan>> = mock()

        loansRepository.create(newLoan).subscribeWith(observer)

        verify(observer).onSuccess(ResultType.Error(errorType))
    }

    @Test
    fun `WHEN get all cached loans EXPECT returns list of loans`() {
        whenever(localDataSource.getLoans()).thenReturn(Single.just(listOf(loan)))

        val observer: SingleObserver<List<Loan>> = mock()

        loansRepository.getAllCached().subscribeWith(observer)

        verify(observer).onSuccess(listOf(loan))
    }

    @Test
    fun `WHEN add loan to cache EXPECT complete`() {
        whenever(localDataSource.addLoans(listOf(loan))).thenReturn(Completable.complete())

        val observer: CompletableObserver = mock()

        loansRepository.addToCache(listOf(loan)).subscribeWith(observer)

        verify(observer).onComplete()
    }
}