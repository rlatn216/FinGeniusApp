package com.skim.core.ui.base

import android.os.Process
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skim.core.common.state.CommonDialogData
import com.skim.core.common.state.PopupState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

open class BaseActivityViewModel : ViewModel() {

    var networkAvailable = false

    var basicDialogData by mutableStateOf<CommonDialogData?>(null)
    var alertDialogData by mutableStateOf<CommonDialogData?>(null)
    var globalDialogData by mutableStateOf<CommonDialogData?>(null)

    fun requestDismissBaseDialog(state: PopupState) {
        basicDialogData?.onDismissRequest?.invoke(state)
        basicDialogData = null
    }

    fun requestDismissAlertDialog(state: PopupState) {
        alertDialogData?.onDismissRequest?.invoke(state)
        alertDialogData = null
    }

    fun requestDismissGlobalDialog(state: PopupState) {
        globalDialogData?.onDismissRequest?.invoke(state)
        globalDialogData = null
    }

    // FloatingDebugLog
    var isShowFloatingLog by mutableStateOf(false)
    var filterType by mutableStateOf("WebView")
    var errorMessage by mutableStateOf("")
    val mainLogLines = mutableStateListOf<String>()
    val curLogLines = mutableStateListOf<String>()
    val maxLogSize = 1000
    var isAutoPaused by mutableStateOf(false)
    var isLogPaused by mutableStateOf(true)

    private val coroutineScope = viewModelScope

    init {
        viewModelScope.launch {
            if (filterType == "") filterType = "WebView"

            addLogMessage("로그가 시작되었습니다. [logState]")
            addLogMessage("\"$filterType\" 로그로 변경되었습니다. [logState]")

            withContext(Dispatchers.IO) {
                val pid = Process.myPid()
                val commandArray = listOf("logcat", "-v", "time", "--pid=$pid", "*:D")
                val logcat = Runtime.getRuntime().exec(commandArray.toTypedArray())
                val br = BufferedReader(InputStreamReader(logcat.inputStream), 4 * 1024)
                val ignoredStrings = setOf(
                    "ViewRootImpl",
                    "BLASTBufferQueue",
                    "InsetsSourceConsumer",
                    "InsetsController",
                    "OpenGLRenderer",
                    "Choreographer",
                    "InputManager",
                    "bufferpool",
                    "ApkAssets: ",
                    "TrafficStats",
                    "inzisoft.paperless.update",
                    "/input",
                    "ConnectivityManager",
                    "CameraPreviewInterface",
                    "IZMobileReader",
                    "SurfaceControl"
                )

                var line: String

                while (br.readLine().also { line = it } != null) {
                    if (isLogPaused) continue

                    val substringLine = if (line.length > 100) line.substring(0, 100) else line
                    val substringLowerLine = substringLine.lowercase(Locale.getDefault())

                    if (!ignoredStrings.any { substringLine.contains(it) }) {
                        if (!isAutoPaused) {
                            // 조건에 따라 isAutoPaused 상태 변경
                            when {
                                substringLowerLine.contains("content-length: ") -> {
                                    line.lowercase(Locale.getDefault())
                                        .substringAfter("content-length: ")
                                        .trim().toIntOrNull()?.let {
                                            if (it > 2000) isAutoPaused = true
                                        }
                                }

                                substringLine.contains("전자문서 로드 시작") -> {
                                    isAutoPaused = true
                                }
                            }

                            // 무시할 문자열이 포함되지 않은 경우 로그 메시지 추가
                            addLogMessage(line)
                            if (substringLine.contains(" E/") && substringLine.contains("[BIZ]")) {
                                errorMessage = "비즈 에러 발생."
                            }
                        } else {
                            // 특정 조건에 따라 isPaused 상태 변경 및 로그 메시지 추가
                            when {
                                substringLine.contains("FinGenius") -> {
                                    isAutoPaused = false
                                    addLogMessage(line)
                                }

                                (substringLine.contains(" E/") && substringLine.contains("[BIZ]")) -> {
                                    addLogMessage(line)
                                    errorMessage = "비즈 에러 발생."
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun shouldAddToLog(filterType: String, message: String): Boolean {
        return when (filterType) {
            "ALL" -> true
            "WebView" -> message.contains("logState") ||
                    message.contains("WebInterface") ||
                    message.contains("responseNative") ||
                    message.contains("webViewLog") ||
                    message.contains("Cookie") ||
                    message.contains("chromium")

            "BizLogic" -> message.contains("logState") ||
                    message.contains("paperless") ||
                    message.contains("[BIZ]") ||
                    message.contains("FinGenius")

            else -> true
        }
    }

    fun updateFilterType(filterType: String) {
        coroutineScope.launch {
            curLogLines.clear()

            curLogLines.addAll(
                mainLogLines.filter { shouldAddToLog(filterType, it) }
            )
        }
    }

    fun addLogMessage(message: String) = coroutineScope.launch(Dispatchers.Main) {
        if (mainLogLines.size >= maxLogSize)
            repeat(100) { mainLogLines.removeAt(0) } // 메모리 과다 사용 방지 (오래된순 100개 삭제)
        val formattedMessage = message
        mainLogLines.add(formattedMessage)

        // Check if the new message fits the current filter type
        if (shouldAddToLog(filterType, formattedMessage)) {
            if (curLogLines.size >= maxLogSize)
                repeat(100) { curLogLines.removeAt(0) }
            curLogLines.add(formattedMessage)
        }
    }
}