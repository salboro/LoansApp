package com.example.loansapp.data.network

import com.example.loansapp.data.db.entity.LoanDb
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    val name: String,
    val role: String
)

@JsonClass(generateAdapter = true)
data class Loan(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val amount: Double,
    val percent: Double,
    val phoneNumber: String,
    val period: Int,
    val date: String,
    val state: String
) {
    fun convertToLoanDb(userName: String) =
        LoanDb(id, userName, firstName, lastName, amount, percent, phoneNumber, period, date, state)
}

@JsonClass(generateAdapter = true)
data class LoansConditions(
    val percent: Double,
    val period: Int,
    val maxAmount: Int
)