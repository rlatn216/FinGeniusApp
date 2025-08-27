package com.skim.fingeniusapp.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaMetadataRetriever
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.skim.core.common.util.PathManager
import com.skim.core.data.repository.WebViewRepository
import com.skim.core.ui.base.BaseActivityViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val pathManager: PathManager,
    val webViewRepository: WebViewRepository,
    @ApplicationContext context: Context
) : BaseActivityViewModel() {

    fun runVaccineModule(context: Context?) {
        //TODO : 백신 라이브러리 앱쪽에 추가
    }
}