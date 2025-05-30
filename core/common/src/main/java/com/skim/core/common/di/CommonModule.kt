package com.skim.core.common.di

import com.skim.core.common.BuildConfig
import com.skim.core.common.util.JsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PrettyJson

    @Provides
    @Singleton
    @PrettyJson
    fun providesPrettyJson(): Json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        if (BuildConfig.DEBUG) {
            prettyPrint = true
        }
    }

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideJsonUtil(json: Json, @PrettyJson prettyJson: Json): JsonUtil = JsonUtil(json, prettyJson)

}