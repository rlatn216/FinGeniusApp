package com.skim.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.skim.core.datastore.AccessToken
import com.skim.core.datastore.PreferenceDataSource
import com.skim.core.datastore.migration.AccessTokenAddCookieMigration
import com.skim.core.datastore.serializer.AccessTokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferenceDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSource(context)

    @Provides
    @Singleton
    fun provideAccessTokenDataStore(@ApplicationContext context: Context): DataStore<AccessToken> =
        DataStoreFactory.create(
            serializer = AccessTokenSerializer,
            migrations = listOf(
                AccessTokenAddCookieMigration
            )
        ) {
            context.dataStoreFile("access_token.pb")
        }

}