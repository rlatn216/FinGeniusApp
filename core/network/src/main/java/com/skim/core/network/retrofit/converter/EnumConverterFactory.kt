package com.skim.core.network.retrofit.converter

import kotlinx.serialization.SerialName
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object EnumConverterFactory : Converter.Factory() {

    // retrofit 에서 enum class 파싱할 때 쓰는거

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<Enum<*>, String>? = if (type is Class<*> && type.isEnum) {
        Converter { enum ->
            try {
                enum.javaClass.getField(enum.name)
                    .getAnnotation(SerialName::class.java)?.value
            } catch (e: Exception) {
                null
            } ?: enum.toString()
        }
    } else null
}