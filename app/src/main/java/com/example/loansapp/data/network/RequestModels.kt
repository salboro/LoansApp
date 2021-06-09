package com.example.loansapp.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    val name: String,
    val role: String
)