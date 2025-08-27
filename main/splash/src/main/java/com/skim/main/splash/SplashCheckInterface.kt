package com.skim.main.splash

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.flow.Flow

interface SplashCheckInterface {
    fun checkRooting(context: Context): Boolean
    fun checkMdm(activity: Activity): Flow<Pair<Boolean, Any?>>
}