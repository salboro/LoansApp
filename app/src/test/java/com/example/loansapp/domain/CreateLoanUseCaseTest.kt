package com.example.loansapp.domain

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.entity.NewLoan
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import com.example.loansapp.domain.usecase.CreateLoanUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CreateLoanUseCaseTest {
    private val newLoan = NewLoan(
        firstName = "test",
        lastName = "test",
        amount = 10,
        percent = 10.0,
        phoneNumber = "123",
        period = 45,
    )

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

    private val loansRepository: LoansRepository = mock()
    private val createLoanUseCase = CreateLoanUseCase(loansRepository)

    @Test
    fun `WHEN createLoanUseCase(newLoan) EXPECT loan`() {
        whenever(loansRepository.create(newLoan)).thenReturn(Single.just(ResultType.Success(loan)))

        val loan = createLoanUseCase(newLoan)

        assertEquals(loan, loansRepository.create(newLoan))
    }
}