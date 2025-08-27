package com.skim.core.common.di

import android.content.Context
import android.os.Environment
import com.skim.core.common.BuildConfig
import com.skim.core.common.util.JsonUtil
import com.skim.core.common.util.PathManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providePathManager(@ApplicationContext context: Context): PathManager {
        return PathManager(
            filesPath = context.filesDir.absolutePath,
            cachePath = context.cacheDir.absolutePath,
            externalFilesPath = context.getExternalFilesDir(null)?.absolutePath,
            externalDownLoadsPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath,
            externalDocumentsPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath,
            externalCachePath = context.externalCacheDir?.absolutePath
        )
    }

}