package com.skim.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(private val context: Context) {
    private val Context.preferenceDataStore by preferencesDataStore(name = "preferenceDataStore")

    // 읽기 사용예시
//    preferenceDataSource.getPreference("auto_login")
//    .onEach { enabled -> … }
//    .launchIn(viewModelScope)

    // 쓰기 사용예시
//    viewModelScope.launch {
//        preferenceDataSource.setPreference("auto_login", "true")
//    }


    fun getPreference(key: String): Flow<String> {
        return context.preferenceDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val key = stringPreferencesKey(key)
                preferences[key] ?: ""
            }
    }

    suspend fun setPreference(key: String, text: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.preferenceDataStore.edit { preferences ->
            preferences[preferenceKey] = text
        }
    }

    suspend fun removePreference(key: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.preferenceDataStore.edit {
            it.remove(preferenceKey)
        }
    }

    suspend fun clear() {
        context.preferenceDataStore.edit {
            it.clear()
        }
    }
}