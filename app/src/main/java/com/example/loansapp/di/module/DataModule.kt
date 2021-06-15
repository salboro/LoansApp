package com.example.loansapp.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.loansapp.data.network.LoansApiService
import com.example.loansapp.di.converter.ConverterFactory
import com.example.loansapp.di.scope.AppScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

private const val BASE_URL = "http://103.23.208.205:8082/"

@Module
class DataModule {
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

    @AppScope
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @AppScope
    @Provides
    fun provideConverterFactory(moshi: Moshi): ConverterFactory =
        ConverterFactory(moshi)

    @AppScope
    @Provides
    fun provideRetrofit(converterFactory: ConverterFactory): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(converterFactory)
        .baseUrl(BASE_URL)
        .build()

    @AppScope
    @Provides
    fun provideLoansApiService(retrofit: Retrofit): LoansApiService =
        retrofit.create(LoansApiService::class.java)
}