package com.example.loansapp.domain

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.repository.LoansRepository
import com.example.loansapp.domain.usecase.GetCachedLoansUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCachedLoansUseCaseTest {
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
        )
    )

    private val loansRepository: LoansRepository = mock()
    private val getCachedLoansUseCase = GetCachedLoansUseCase(loansRepository)

    @Test
    fun `WHEN getCachedLoansUseCase() EXPECT List Loan`() {
        whenever(loansRepository.getAllCached()).thenReturn(Single.just(loansList))

        val loans = getCachedLoansUseCase()

        assertEquals(loans, loansRepository.getAllCached())
    }
}