package com.skim.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skim.core.datastore.MainDataSource
import com.skim.core.model.BaseConfig
import com.skim.core.network.retrofit.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CryptoRetrofit

    @CryptoRetrofit
    @Singleton
    @Provides
    fun provideCryptoRetrofit(
        okHttpClient: OkHttpClient,
//        cryptoService: CryptoService,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(MediaType.get("application/json"))
            )
//            .addConverterFactory(EnumConverterFactory)
            .baseUrl("${BaseConfig.SERVER_PROTOCOL}://${BaseConfig.API_SERVER_URL}")
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .baseUrl("${BaseConfig.SERVER_PROTOCOL}://${BaseConfig.API_SERVER_URL}")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(mainDataSource: MainDataSource): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(BaseConfig.TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(BaseConfig.TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor(mainDataSource))

        if (BaseConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }

        return httpClientBuilder.build()
    }

//    @Provides
//    @Singleton
//    fun providesBaseNetworkDataSource(
//        json: Json,
//        mainDataSource: MainDataSource,
//        cryptoService: CryptoService,
//    ): RetrofitNetworkDataSource =
//        RetrofitNetworkDataSource(json, mainDataSource, cryptoService)
}