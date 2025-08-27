package com.skim.fingeniusapp

import android.app.Application
import com.skim.core.model.BaseConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initBaseConfig()
    }

    private fun initBaseConfig() {
        BaseConfig.apply {

        }
    }

}