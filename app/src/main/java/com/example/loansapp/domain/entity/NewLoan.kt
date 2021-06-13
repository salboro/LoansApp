package com.example.loansapp.domain.entity

data class NewLoan(
    val firstName: String,
    val lastName: String,
    val amount: Int,
    val percent: Double,
    val phoneNumber: String,
    val period: Int
)