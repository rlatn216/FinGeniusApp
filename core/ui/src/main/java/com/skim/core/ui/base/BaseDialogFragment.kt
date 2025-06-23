package com.skim.core.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.clearFragmentResultListener
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.skim.core.common.state.Cancel
import com.skim.core.common.state.CommonAlertData
import com.skim.core.common.state.Left
import com.skim.core.common.state.PopupState
import com.skim.core.common.state.Right
import com.skim.core.common.util.FragmentRequest
import com.skim.core.common.util.FragmentResult
import com.skim.core.common.util.setFragmentResultListener
import com.skim.core.designsystem.component.AlertDialog
import com.skim.core.designsystem.component.BasicDialog
import com.skim.core.designsystem.theme.SKimTheme
import com.skim.core.model.BaseConfig
import com.skim.core.model.BaseLog
import com.skim.core.model.log.EventLogger
import com.skim.core.ui.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    protected val baseCompose = BaseCompose()

    abstract fun getBaseViewModel(): BaseDialogFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullscreenDialogTheme)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        BaseLog.i("[${this.javaClass.simpleName}] 시작")
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                if (baseCompose.topBar == null) {
                    SKimTheme {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                        ) {
                            baseCompose.content()
                            baseCompose.surface?.invoke()
                        }
                        // 현재 버전 상태 info UI
                        if (stringResource(id = R.string.env_name).isNotBlank()) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = stringResource(id = R.string.env_name) + " / v" + BaseConfig.VERSION_NAME,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(10.dp),
                                    color = colorResource(id = R.color.env_name),
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(
                                        color = Color.Black, shadow = Shadow(
                                            color = Color.Black, blurRadius = 2f
                                        )
                                    )
                                )
                            }
                        }
                    }
                } else {
                    baseCompose.baseScreen.invoke()
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            with(WindowCompat.getInsetsController(this, decorView)) {
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                hide(WindowInsetsCompat.Type.systemBars())
            }
        }
        return dialog
    }

    override fun onResume() {
        super.onResume()
        BaseLog.eventLogger?.event(
            EventLogger.Event.SCREEN_VIEW,
            mapOf(
                EventLogger.Param.SCREEN_NAME to this.javaClass.simpleName,
                EventLogger.Param.SCREEN_CLASS to this.javaClass.simpleName
            )
        )
    }

    override fun onDestroyView() {
        BaseLog.i("[${this.javaClass.simpleName}] 종료")
        super.onDestroyView()
    }

    protected fun availableNetwork(): Boolean {
        val mainActivity = activity as? BaseActivityInterface ?: return false
        return mainActivity.networkAvailable()
    }

    protected fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination) }
    }

    protected fun hideKeyboard() {
        activity?.let { BaseUiUtil.hideKeyboard(it) }
    }

    @Composable
    protected fun ShowBasicDialog(
        @StringRes titleText: Int,
        @StringRes contentText: Int,
        @StringRes leftBtnText: Int? = null,
        @StringRes rightBtnText: Int,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        ShowBasicDialog(
            getString(titleText),
            getString(contentText),
            leftBtnText?.let { getString(it) },
            getString(rightBtnText),
            onDismissRequest
        )
    }

    @Composable
    protected fun ShowBasicDialog(
        titleText: String,
        contentText: String,
        leftBtnText: String? = null,
        rightBtnText: String,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        ShowBasicDialog(
            titleText,
            buildAnnotatedString {
                append(contentText)
            },
            leftBtnText,
            rightBtnText,
            onDismissRequest
        )
    }

    @Composable
    protected fun ShowBasicDialog(
        titleText: String,
        contentText: AnnotatedString,
        leftBtnText: String? = null,
        rightBtnText: String,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x33000000))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { },
            contentAlignment = Alignment.Center
        ) {
            BasicDialog(
                titleText = titleText,
                contentText = contentText,
                leftBtnText = leftBtnText,
                rightBtnText = rightBtnText,
                onClosed = { onDismissRequest(Cancel) },
                onLeftBtnClick = { onDismissRequest(Left) },
                onRightBtnClick = { onDismissRequest(Right) }
            )
        }
    }

    @Composable
    protected fun ShowAlertDialog(
        @StringRes contentText: Int,
        @StringRes leftBtnText: Int? = null,
        @StringRes rightBtnText: Int,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        ShowAlertDialog(
            getString(contentText),
            leftBtnText?.let { getString(it) },
            getString(rightBtnText),
            onDismissRequest
        )
    }

    @Composable
    protected fun ShowAlertDialog(
        contentText: String,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        ShowAlertDialog(
            buildAnnotatedString {
                append(contentText)
            },
            leftBtnText,
            rightBtnText,
            onDismissRequest
        )
    }

    @Composable
    protected fun ShowAlertDialog(
        contentText: AnnotatedString,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x33000000))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { },
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                contentText = contentText,
                leftBtnText = leftBtnText,
                rightBtnText = rightBtnText,
                onLeftBtnClick = { onDismissRequest(Left) },
                onRightBtnClick = { onDismissRequest(Right) }
            )
        }
    }

//    @SuppressLint("SimpleDateFormat")
//    protected fun showDatePicker(
//        maxDate: Date? = null,
//        currentDate: String?,
//        onDateChangeListener: (Date) -> Unit
//    ) {
//        setFragmentResultListener(FragmentRequest.DatePicker) { result ->
//            clearFragmentResultListener(FragmentRequest.DatePicker.key)
//
//            when (result) {
//                is FragmentResult.OK -> {
//
//                    val resultStr = result.data.orEmpty()
//                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//
//                    try {
//                        val date: Date? = dateFormat.parse(resultStr)
//                        date?.run(onDateChangeListener)
//                    } catch (e: Exception) {
//                        BaseLog.e("${e.message}")
//                    }
//
//                }
//
//                else -> {}
//            }
//        }
//
//        val dateFormat = SimpleDateFormat("yyyyMMdd")
//        val date: Date = try {
//            currentDate?.let { dateFormat.parse(it) }
//        } catch (e: Exception) {
//            null
//        } ?: Date()
//
//        val year = SimpleDateFormat("yyyy").format(date).toInt()
//        val month = SimpleDateFormat("MM").format(date).toInt() - 1
//        val day = SimpleDateFormat("dd").format(date).toInt()
//
//        navigate(
//            DatePickerDialogFragmentDirections.actionGlobalDatePickerDialogFragment(
//                year = year,
//                month = month,
//                day = day,
//                maxDate = maxDate
//            )
//        )
//    }

    protected fun terminateApplication() {
        (activity as BaseActivityInterface).quitApplication()
    }

    @Composable
    protected fun ShowFloatingDebug(message: String) {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.ShowFloatingDebug(message = message)
    }

    @Composable
    protected fun ShowRecord(showAlertDialog: (commonAlertData: CommonAlertData) -> Unit) {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.ShowRecord {
            showAlertDialog(
                CommonAlertData(
                    it.contentText.toString(),
                    it.leftBtnText,
                    it.rightBtnText,
                    it.onDismissRequest
                )
            )
        }
    }
}