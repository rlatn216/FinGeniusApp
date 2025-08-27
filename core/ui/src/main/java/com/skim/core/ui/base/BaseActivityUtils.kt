package com.skim.core.ui.base

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.skim.core.common.state.Cancel
import com.skim.core.common.state.CommonDialogData
import com.skim.core.common.state.Left
import com.skim.core.common.state.Right
import com.skim.core.designsystem.component.AlertDialog
import com.skim.core.designsystem.component.BasicDialog
import com.skim.core.designsystem.component.FloatingDebugLog
import com.skim.core.designsystem.component.GlobalDialog
import com.skim.core.ui.R
import java.util.Locale
import kotlin.system.exitProcess


class BaseActivityUtils(
    val context: Context,
    val baseActivityViewModel: BaseActivityViewModel
) {

    fun processOnCreate(activity: Activity, permissions: Array<String>) {
        activity.installSplashScreen()

        registerNetworkCallback()
        checkPermissions(activity, permissions, 1)
//        ttsPromptInstall(activity)
    }

    fun processOnDestroy() {
        unRegisterNetworkCallback()
    }

    fun networkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                    ?: return false

            connectivityManager.activeNetwork?.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.apply {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        )
                    ) {
                        baseActivityViewModel.networkAvailable = true
                    }
                }
            }
        }


        return baseActivityViewModel.networkAvailable
    }

    private fun registerNetworkCallback() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return

        connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.apply {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR
                    )
                ) {
                    baseActivityViewModel.networkAvailable = true
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }
    }

    private fun unRegisterNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                    ?: return
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    fun showBasicDialog(dialogData: CommonDialogData) {
        baseActivityViewModel.basicDialogData = dialogData
    }

    fun dismissBasicDialog() {
        baseActivityViewModel.requestDismissBaseDialog(Cancel)
    }

    fun showAlertDialog(dialogData: CommonDialogData) {
        baseActivityViewModel.alertDialogData = dialogData
    }

    fun dismissAlertDialog() {
        baseActivityViewModel.requestDismissAlertDialog(Cancel)
    }

    fun showGlobalDialog(dialogData: CommonDialogData) {
        baseActivityViewModel.globalDialogData = dialogData
    }

    fun dismissGlobalDialog() {
        baseActivityViewModel.requestDismissGlobalDialog(Cancel)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            baseActivityViewModel.networkAvailable = true
            Log.i("NetworkCallback", "network available")
        }

        override fun onLost(network: Network) {
            baseActivityViewModel.networkAvailable = false
            Log.e("NetworkCallback", "network unavailable")
        }
    }

    @Composable
    fun ShowBaseDialog(dialogData: CommonDialogData) {
        BasicDialog(
            titleText = dialogData.titleText,
            contentText = dialogData.contentText,
            onClosed = { baseActivityViewModel.requestDismissBaseDialog(Cancel) },
            leftBtnText = dialogData.leftBtnText,
            onLeftBtnClick = { baseActivityViewModel.requestDismissBaseDialog(Left) },
            rightBtnText = dialogData.rightBtnText,
            onRightBtnClick = { baseActivityViewModel.requestDismissBaseDialog(Right) }
        )
    }

    @Composable
    fun ShowAlertDialog(dialogData: CommonDialogData) {
        AlertDialog(
            contentText = dialogData.contentText,
            leftBtnText = dialogData.leftBtnText,
            onLeftBtnClick = { baseActivityViewModel.requestDismissAlertDialog(Left) },
            rightBtnText = dialogData.rightBtnText,
            onRightBtnClick = { baseActivityViewModel.requestDismissAlertDialog(Right) }
        )
    }

    @Composable
    fun ShowGlobalDialog(dialogData: CommonDialogData){
        GlobalDialog(
            contentText = dialogData.contentText,
            leftBtnText = dialogData.leftBtnText,
            onLeftBtnClick = { baseActivityViewModel.requestDismissGlobalDialog(Left) },
            rightBtnText = dialogData.rightBtnText,
            onRightBtnClick = { baseActivityViewModel.requestDismissGlobalDialog(Right) }
        )
    }

    fun showAlertExpiredSession() {
        baseActivityViewModel.globalDialogData = CommonDialogData(
            titleText = "",
            contentText = buildAnnotatedString {
                append(context.getString(R.string.skim_ui_expired_session))
            },
            rightBtnText = context.getString(R.string.skim_ui_confirm)
        ) {
            restartApplication()
        }
    }

    fun restartApplication() {
        context.startActivity(
            Intent.makeRestartActivityTask(
                context.packageManager.getLaunchIntentForPackage(
                    context.packageName
                )?.component
            )
        )
        exitProcess(0)
    }

    fun quitApplication(activity: Activity) {
        activity.finish()
        exitProcess(0)
    }


    // requestPermissions() 여기서 permissions 배열 넘겨주면 아래 메서드 호출된다
    fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            // 안드로이드 13이상일 시, 그 권한이 WRITE_EXTERNAL_STORAGE 이라면 스킵.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                for (permissionIdx in permissions.indices) {
                    if (permissions[permissionIdx] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        grantResults[permissionIdx] = PackageManager.PERMISSION_GRANTED
                    }
                }
            }
            val isGranted: Boolean = checkUserAcceptPermissions(grantResults)
            if (!isGranted) {
                showAlertDialog(
                    CommonDialogData(
                        titleText = context.getString(R.string.skim_ui_toast_msg_splash_permission_need_title),
                        contentText = buildAnnotatedString {
                            append(context.getString(R.string.skim_ui_toast_msg_splash_permission_denied))
                        },
                        rightBtnText = context.getString(R.string.skim_ui_move_to_app_setting)
                    ) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:${context.packageName}"))
                        activity.startActivity(intent)
                        activity.finish()
                    }
                )
            }
        }
    }


    // 현재 앱이 허용(PERMISSION_GRANTED) 상태 인지 체크후 허용되지 않은 permission list에 담아 requestPermissions 이거 호출
    private fun checkPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var result: Int
            val permissionList: MutableList<String> = ArrayList()
            for (pm in permissions) {
                result = ContextCompat.checkSelfPermission(context, pm)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(pm)
                }
            }
            if (permissionList.isNotEmpty()) {
                activity.requestPermissions(permissionList.toTypedArray(), requestCode)
            }
        }
    }

    // 앱에서 사용되는 permissions 모두 허용이 되었는지 체크
    private fun checkUserAcceptPermissions(grantResults: IntArray): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (grantResults.isEmpty()) {
            return false
        }
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    @Composable
    fun ShowFloatingDebug(message: String) {
        val listState = rememberLazyListState()

        FloatingDebugLog(
            isShowFloatingLog = baseActivityViewModel.isShowFloatingLog,
            mainLogLines = baseActivityViewModel.mainLogLines,
            curLogLines = baseActivityViewModel.curLogLines,
            listState = listState,
            filterType = baseActivityViewModel.filterType,
            onFilterType = {
                baseActivityViewModel.filterType = it
                baseActivityViewModel.updateFilterType(it)
                baseActivityViewModel.addLogMessage("\"$it\" 로그로 변경되었습니다. [logState]")
            },
            isAutoPaused = baseActivityViewModel.isAutoPaused,
            isLogPaused = baseActivityViewModel.isLogPaused,
            onLogPause = {
                baseActivityViewModel.isLogPaused = !baseActivityViewModel.isLogPaused
                if (baseActivityViewModel.isLogPaused) baseActivityViewModel.addLogMessage("로그가 중지되었습니다. [logState]")
                else baseActivityViewModel.addLogMessage("로그가 시작되었습니다. [logState]")
            },
            onClear = {
                baseActivityViewModel.mainLogLines.clear()
                baseActivityViewModel.curLogLines.clear()
            },
            message = message,
            errorMessage = baseActivityViewModel.errorMessage,
            onErrorMessage = { baseActivityViewModel.errorMessage = it }
        )
    }

//    private fun ttsPromptInstall(activity: Activity) {
//        lateinit var tts: TextToSpeech
//        tts = TextToSpeech(context) {
//            val result = tts.setLanguage(Locale.KOREAN)
//
//            // 한국어 지원 여부 확인
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "Korean language is not supported or missing!")
//
//                baseActivityViewModel.alertDialogData = CommonDialogData(
//                    titleText = "",
//                    contentText = buildAnnotatedString {
//                        append("TTS를 사용하려면 한국어 TTS를 다운로드해야 합니다.")
//                    },
//                    rightBtnText = context.getString(R.string.skim_ui_confirm)
//                ) {
//                    // 한국어 데이터가 없으면 설치 유도
//                    val installIntent = Intent("com.android.settings.TTS_SETTINGS").apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    }
//                    context.startActivity(installIntent)
//                    quitApplication(activity)
//                }
//            } else {
//                Log.d("TTS", "Korean language is support")
//            }
//        }
//    }
}