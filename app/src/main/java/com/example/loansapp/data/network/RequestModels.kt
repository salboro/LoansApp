package com.example.loansapp.data.network

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    val name: String,
    val role: String
)

@JsonClass(generateAdapter = true)
data class Loan(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val amount: Double,
    val percent: Double,
    val phoneNumber: String,
    val period: Int,
    val date: Date,
    val state: String
)

@JsonClass(generateAdapter = true)
data class LoansConditions(
    val percent: Double,
    val period: Int,
    val maxAmount: Int
)