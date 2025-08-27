package com.skim.core.network.retrofit

import com.skim.core.datastore.MainDataSource
import com.skim.core.model.ApiErrorCode
import com.skim.core.model.ApiException
import com.skim.core.model.ExceptionCode.EXCEPTION_CODE_FAIL_API
import com.skim.core.model.ExceptionCode.EXCEPTION_CODE_NOT_AVAILABLE_NETWORK
import com.skim.core.network.model.ApiResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val SUCCESS = 200

@Singleton
class RetrofitNetworkDataSource @Inject constructor(
    private val json: Json,
    private val mainDataSource: MainDataSource,
) {

    suspend inline fun <reified T> call(request: () -> Response<ApiResponse<T>>): T =
        try {
            val response = request()

            if (response.isSuccessful) {
                val body = response.body() as? ApiResponse<*>
                    ?: throw NullPointerException("Body is empty.")

                if (body.status == SUCCESS) {
                    body.data as T
                } else {
                    throw ApiException(body.status, body.message)
                }
            } else {
                throw response.toApiException()
            }

        } catch (apiException: ApiException) {
            throw apiException
        } catch (e: IOException) {
            throw ApiException(EXCEPTION_CODE_NOT_AVAILABLE_NETWORK, "${e.message}", e)
        } catch (e: Exception) {
            throw ApiException(EXCEPTION_CODE_FAIL_API, "${e.message}", e)
        }

    suspend inline fun <reified T> call(
        headers: ((Map<String, List<String>>) -> Unit),
        request: () -> Response<ApiResponse<T>>,
    ): T =
        try {
            val response = request()

            if (response.isSuccessful) {
                val body = response.body() as? ApiResponse<*>
                    ?: throw NullPointerException("Body is empty.")

                if (body.status == SUCCESS) {
                    headers(response.headers().toMultimap())
                    body.data as T
                } else {
                    throw ApiException(body.status, body.message)
                }
            } else {
                throw response.toApiException()
            }

        } catch (apiException: ApiException) {
            throw apiException
        } catch (e: IOException) {
            throw ApiException(EXCEPTION_CODE_NOT_AVAILABLE_NETWORK, "${e.message}", e)
        } catch (e: Exception) {
            throw ApiException(EXCEPTION_CODE_FAIL_API, "${e.message}", e)
        }


    @OptIn(ExperimentalSerializationApi::class)
    suspend fun <T> Response<T>.toApiException(): ApiException {
        val errorBody = errorBody() ?: return ApiException(code(), message())

        val apiResponse: ApiResponse<Nothing> = try {
            json.decodeFromStream(errorBody.byteStream())
        } catch (e: Exception) {
            ApiResponse(code(), errorBody.string())
        }

        if (code() == 401 && apiResponse.status == ApiErrorCode.ERROR_STATUS_INVALID_TOKEN) {
            mainDataSource.onExpired()
        }

        return ApiException(apiResponse.status, apiResponse.message)
    }

}