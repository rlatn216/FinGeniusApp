package com.skim.core.datastore

import androidx.datastore.core.DataStore
import com.skim.core.common.util.PathManager
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QServiceDataSource @Inject constructor(
    private val accessTokenDataStore: DataStore<AccessToken>,
    private val pathManager: PathManager
) {
    val accessToken = accessTokenDataStore.data.map { it.accessToken }

    val isExpired = accessTokenDataStore.data.map { it.isExpired }

    val cookie = accessTokenDataStore.data.map { it.cookie }


    suspend fun setAccessToken(newToken: String) {
        accessTokenDataStore.updateData {
            it.copy {
                accessToken = newToken
                isExpired = false
            }
        }
    }

    suspend fun clearAccessToken() {
        accessTokenDataStore.updateData {
            it.copy {
                clearAccessToken()
                clearIsExpired()
            }
        }
    }

    suspend fun onExpired() {
        accessTokenDataStore.updateData {
            it.copy {
                clearAccessToken()
                isExpired = true
            }
        }
    }

    suspend fun setCookie(newCookie: String) {
        accessTokenDataStore.updateData {
            it.copy {
                cookie = newCookie
            }
        }
    }

    suspend fun clearCookie() {
        accessTokenDataStore.updateData {
            it.copy {
                clearCookie()
            }
        }
    }
}