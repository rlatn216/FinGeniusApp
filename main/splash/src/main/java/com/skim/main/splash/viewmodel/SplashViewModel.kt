package com.skim.main.splash.viewmodel

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.skim.core.common.util.FileUtils
import com.skim.core.common.util.PathManager
import com.skim.core.data.repository.WebViewRepository
import com.skim.core.model.BaseConfig
import com.skim.core.model.BaseLog
import com.skim.core.ui.base.BaseViewModel
import com.skim.main.splash.SplashCheckInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class SplashViewModel(
    pathManager: PathManager,
    webViewRepository: WebViewRepository,
//    cameraRepository: CameraRepository,
//    eDocRepository: EDocRepository
) : BaseViewModel() {

    var showProgressState by mutableStateOf(true)
    var checkResult by mutableStateOf<SplashCheckResult>(SplashCheckResult.None)
    var checkStepState by mutableStateOf<SplashCheckState>(SplashCheckState.None)
//    private val appVersionCheckInterface by lazy { initAppVersionCheckInterface() }
    private val splashCheckInterface by lazy { initSplashCheckInterface() }

    init {
        BaseLog.i(pathManager.getLogDir())
        viewModelScope.launch {
            FileUtils.delete(pathManager.getEdocIdDir())
            webViewRepository.clearAll()
//            cameraRepository.clear()
//            eDocRepository.clearAllMobileEditorResult()
        }
    }

//    abstract fun initAppVersionCheckInterface(): AppVersionCheckInterface
    abstract fun initSplashCheckInterface(): SplashCheckInterface

    fun checkRootingStatus(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        BaseLog.i("1. 루팅 체크 시작")
        val isRooting = splashCheckInterface.checkRooting(context = context)
        if (isRooting) {
            BaseLog.i("=> 루팅됨")
            checkResult = SplashCheckResult.RootingCheckError
        } else {
            BaseLog.i("=> 루팅 되지 않음")
            checkStepState = SplashCheckState.NetworkCheck
        }
        checkStepState = SplashCheckState.NetworkCheck
    }

    fun checkApplicationVersion() = viewModelScope.launch(Dispatchers.IO) {
        BaseLog.i("3. 앱 버전 체크")

        if (BaseConfig.SKIP_CHECK_VERSION) {
            BaseLog.i("버전 체크 스킵")
            checkResult = SplashCheckResult.SplashWorkFinish
            return@launch
        }

//        appVersionCheckInterface.appVersionCheck { versionCheckResult, responseData ->
//            checkResult = if(versionCheckResult) {
//                SplashCheckResult.NeedApplicationUpdate(responseData)
//            } else {
//                SplashCheckResult.SplashWorkFinish
//            }
//        }
    }

    fun checkMdm(activity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        splashCheckInterface.checkMdm(activity).collect { result ->
            if(result.first) {
                checkStepState = SplashCheckState.AppVersionCheck
            } else {
                checkResult = SplashCheckResult.MdmCheckError(result.second)
            }
        }
    }
}

sealed class SplashCheckState {
    object None : SplashCheckState()
    object RootingCheck : SplashCheckState()
    object NetworkCheck : SplashCheckState()
    object AppVersionCheck : SplashCheckState()
    object MDMCheck: SplashCheckState()
}

sealed class SplashCheckResult {
    object None : SplashCheckResult()
//    data class NeedApplicationUpdate(val applicationVersion: ApplicationVersion?) :
//        SplashCheckResult()
    object RootingCheckError : SplashCheckResult()
    data class MdmCheckError(val errorResponse : Any?) : SplashCheckResult()
    object SplashWorkFinish : SplashCheckResult()
}