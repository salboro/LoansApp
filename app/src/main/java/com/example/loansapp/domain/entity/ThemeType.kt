package com.example.loansapp.domain.entity

sealed class ThemeType {
    object Dark : ThemeType()

    object Light : ThemeType()
}
