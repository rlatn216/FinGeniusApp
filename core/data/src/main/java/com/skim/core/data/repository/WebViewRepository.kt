package com.skim.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject

interface WebViewRepository {

    suspend fun getCookie(): String

//    suspend fun downloadForm(
//        provId: String,
//        folderName: String,
//        fileName: String
//    ): Flow<ByteArray>
//
//    suspend fun downloadForm(
//        provId: String,
//        path: String,
//        onProgress: ((downloadedMb: Float, totalMb: Float, progress: Float) -> Unit)? = null
//    ): Flow<ByteArray>
//
//
//    suspend fun deleteTemp(data: JsonObject)
//
//    suspend fun clearEdocId()

    suspend fun clearToken()

    suspend fun clearAll()

}