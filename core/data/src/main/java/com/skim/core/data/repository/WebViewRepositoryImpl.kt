package com.skim.core.data.repository

import android.content.Context
import com.skim.core.common.network.CommonDispatchers
import com.skim.core.common.network.Dispatcher
import com.skim.core.common.util.JsonUtil
import com.skim.core.common.util.PathManager
import com.skim.core.data.repository.WebViewRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.JsonObject
import javax.inject.Inject

class WebViewRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @Dispatcher(CommonDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val json: JsonUtil,
    private val pathManager: PathManager,
    private val edsNetworkDataSource: EDSNetworkDataSource,
    private val qServiceDataSource: QServiceDataSource
) : WebViewRepository {

    override suspend fun getCookie(): String = qServiceDataSource.cookie.first()


    override suspend fun downloadForm(
        provId: String,
        folderName: String,
        fileName: String
    ): Flow<ByteArray> = flow {
        emit(edsNetworkDataSource.downloadForm(provId, folderName, fileName))
    }.flowOn(ioDispatcher)

    override suspend fun downloadForm(
        provId: String,
        path: String,
        onProgress: ((downloadedMb: Float, totalMb: Float, progress: Float) -> Unit)?
    ): Flow<ByteArray> = flow {
        emit(edsNetworkDataSource.downloadForm(provId, path))
    }.flowOn(ioDispatcher)



    override suspend fun deleteTemp(data: JsonObject) {
        edsNetworkDataSource.deleteTemp(data)
    }

    override suspend fun clearEdocId() {
        qServiceDataSource.clearEdocId()
    }

    override suspend fun clearToken() {
        qServiceDataSource.clearAccessToken()
        qServiceDataSource.clearCookie()
    }

    override suspend fun clearAll() {
        clearEdocId()
        clearToken()
    }

}