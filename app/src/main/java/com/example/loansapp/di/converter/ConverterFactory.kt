package com.example.loansapp.di.converter

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject

class ConverterFactory @Inject constructor(
    private val moshi: Moshi
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation.annotationClass == MoshiConverter::class) {
                return MoshiConverterFactory.create(moshi)
                    .responseBodyConverter(type, annotations, retrofit)
            }
            if (annotation.annotationClass == ScalarsConverter::class) {
                return ScalarsConverterFactory.create()
                    .responseBodyConverter(type, annotations, retrofit)
            }
        }
        return null
    }
}
