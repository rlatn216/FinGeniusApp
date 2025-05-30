package com.skim.core.data.di

import com.skim.core.data.repository.WebViewRepository
import com.skim.core.data.repository.WebViewRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun WebViewRepositoryImpl.bindsWebViewRepository(): WebViewRepository

}