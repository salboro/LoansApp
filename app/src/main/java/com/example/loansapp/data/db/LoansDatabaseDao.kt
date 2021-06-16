package com.example.loansapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loansapp.data.db.entity.LoanDb
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LoansDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLoans(loans: List<LoanDb>): Completable

    @Query("SELECT * FROM loans WHERE user_name = :userName ORDER BY id DESC")
    fun getLoans(userName: String): Single<List<LoanDb>>
}