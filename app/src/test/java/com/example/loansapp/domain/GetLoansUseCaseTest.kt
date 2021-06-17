package com.example.loansapp.domain

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import com.example.loansapp.domain.usecase.GetLoansUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLoansUseCaseTest {

    private val loansList = listOf(
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
            state = "Rejecte"
        )
    )

    private val loansRepository: LoansRepository = mock()
    private val getLoansUseCase = GetLoansUseCase(loansRepository)


    @Test
    fun `WHEN getLoansUseCase() EXPECT list of loans`() {
        whenever(loansRepository.getAll()).thenReturn(Single.just(ResultType.Success(loansList)))

        val loans = getLoansUseCase()

        assertEquals(loansRepository.getAll(), loans)
    }
}