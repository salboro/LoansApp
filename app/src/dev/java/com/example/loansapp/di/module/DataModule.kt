package com.example.loansapp.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.loansapp.data.db.LoansDatabase
import com.example.loansapp.data.db.LoansDatabaseDao
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.di.scope.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://103.23.208.205:8082/"
private const val DATABASE_NAME = "loans_database"

@Module
class DataModule {
    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @AppScope
    @Provides
    fun provideMockService(retrofit: Retrofit): LoansApiService {
        val mockRetrofit =
            MockRetrofit.Builder(retrofit).networkBehavior(NetworkBehavior.create()).build()
        mockRetrofit.networkBehavior().setDelay(500L, TimeUnit.MILLISECONDS)
        mockRetrofit.networkBehavior().setErrorPercent(0)
        val behaviorDelegate = mockRetrofit.create(LoansApiService::class.java)
        return LoansApiMockService(behaviorDelegate)
    }

    @AppScope
    @Provides
    fun provideDB(context: Context): LoansDatabase =
        Room.databaseBuilder(
            context,
            LoansDatabase::class.java,
            DATABASE_NAME
        ).build()

    @AppScope
    @Provides
    fun provideDBDao(dataBase: LoansDatabase): LoansDatabaseDao =
        dataBase.databaseDao

    @AppScope
    @Provides
    fun provideEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            "EncryptedPreferences",
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}