package com.example.loansapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loansapp.data.network.Loan

@Entity(tableName = "loans")
data class LoanDb(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val amount: Double,
    val percent: Double,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    val period: Int,
    val date: String,
    val state: String
) {
    fun convertToLoan(): Loan =
        Loan(id, firstName, lastName, amount, percent, phoneNumber, period, date, state)
}