package com.example.loansapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loansapp.data.db.entity.LoanDb

@Database(entities = [LoanDb::class], version = 1, exportSchema = false)
abstract class LoansDatabase : RoomDatabase() {
    abstract val databaseDao: LoansDatabaseDao
}