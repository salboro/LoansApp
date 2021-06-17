package com.example.loansapp.domain

import com.example.loansapp.data.network.LoansConditions
import com.example.loansapp.domain.entity.ResultType
import com.example.loansapp.domain.repository.LoansRepository
import com.example.loansapp.domain.usecase.GetLoansConditionsUseCase
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLoansConditionsUseCaseTest {

    private val loansConditions = LoansConditions(
        percent = 10.0,
        period = 10,
        maxAmount = 10
    )

    private val loansRepository: LoansRepository = mock()
    private val getLoansConditionsUseCase = GetLoansConditionsUseCase(loansRepository)

    @Test
    fun `WHEN getLoansConditions() EXPECT loansConditions`() {
        whenever(loansRepository.getConditions()).thenReturn(
            Single.just(
                ResultType.Success(
                    loansConditions
                )
            )
        )

        val conditions = getLoansConditionsUseCase()

        assertEquals(conditions, loansRepository.getConditions())
    }
}