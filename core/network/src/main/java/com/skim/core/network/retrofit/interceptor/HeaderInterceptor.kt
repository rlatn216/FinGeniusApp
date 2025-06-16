package com.skim.core.network.retrofit.interceptor

import com.skim.core.datastore.QServiceDataSource
import com.skim.core.model.HeaderKeySet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val qServiceDataSource: QServiceDataSource
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking(Dispatchers.IO) {
        val token = qServiceDataSource.accessToken.first()

        chain.proceed(
            if (token.isEmpty()) {
                chain.request()
            } else {
                chain.request()
                    .newBuilder()
                    .addHeader(HeaderKeySet.ACCESS_TOKEN, token)
                    .build()
            }
        )
    }
}