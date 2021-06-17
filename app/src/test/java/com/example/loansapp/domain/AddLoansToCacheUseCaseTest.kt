package com.example.loansapp.domain

import com.example.loansapp.data.network.Loan
import com.example.loansapp.domain.repository.LoansRepository
import com.example.loansapp.domain.usecase.AddLoansToCacheUseCase
import io.reactivex.Completable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AddLoansToCacheUseCaseTest {
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
    private val addLoansToCacheUseCase = AddLoansToCacheUseCase(loansRepository)

    @Test
    fun `WHEN addLoansToCacheUseCase() EXPECT Completable`() {
        whenever(loansRepository.addToCache(loansList)).thenReturn(Completable.complete())

        val completable = addLoansToCacheUseCase(loansList)

        assertEquals(completable, loansRepository.addToCache(loansList))
    }
}