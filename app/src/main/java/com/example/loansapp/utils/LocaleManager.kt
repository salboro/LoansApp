package com.example.loansapp.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*

class LocaleManager {
    companion object {
        const val RUSSIAN_LANGUAGE_CODE = "ru"
        const val ENGLISH_LANGUAGE_CODE = "en"
        private const val PREFER_LOCALE = "PREFER LOCALE"

        fun setLocale(context: Context): Context {
            //Костыль получается, а как без него - непонятно

            val pref = getPreferences(context)

            val langCode = pref.getString(PREFER_LOCALE, "") ?: ""
            return if (langCode != "" || langCode != getLanguage()) {
                val locale = Locale(langCode)
                Locale.setDefault(locale)
                val config = Configuration(context.resources.configuration)
                config.setLocale(locale)
                context.createConfigurationContext(config)
            } else {
                context
            }
        }

        private fun getLanguage(): String =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList.getDefault().get(0).language
            } else {
                Locale.getDefault().language
            }

        private fun getPreferences(context: Context) =
            EncryptedSharedPreferences.create(
                "EncryptedPreferences",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }


}