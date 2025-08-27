package com.skim.core.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.skim.core.common.state.CommonDialogData
import com.skim.core.common.state.PopupState
import com.skim.core.model.BaseLog
import com.skim.core.model.log.EventLogger
import com.skim.core.ui.R

abstract class BaseFragment : Fragment() {

    protected val baseCompose = BaseCompose()


    abstract fun getViewModel(): BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        BaseLog.i("[${this.javaClass.simpleName}] 시작")
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                baseCompose.baseScreen.invoke()
            }
        }
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

    protected fun onError(throwable: Throwable) {
        getViewModel().onError(throwable)
    }

    protected fun availableNetwork(): Boolean {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return false
        return baseActivityInterface.networkAvailable()
    }

    protected fun hideKeyboard() {
        activity?.let { BaseUiUtil.hideKeyboard(it) }
    }

    protected fun showBasicDialog(
        @StringRes titleText: Int,
        @StringRes contentText: Int,
        @StringRes leftBtnText: Int? = null,
        @StringRes rightBtnText: Int = R.string.skim_ui_confirm,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        showBasicDialog(
            getString(titleText),
            getString(contentText),
            leftBtnText?.let { getString(it) },
            getString(rightBtnText),
            onDismissRequest
        )
    }

    protected fun showBasicDialog(
        titleText: String,
        contentText: String,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        showBasicDialog(
            titleText,
            buildAnnotatedString {
                append(contentText)
            },
            leftBtnText,
            rightBtnText,
            onDismissRequest
        )
    }

    protected fun showBasicDialog(
        titleText: String,
        contentText: AnnotatedString,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.showBasicDialog(
            CommonDialogData(
                titleText,
                contentText,
                leftBtnText,
                rightBtnText,
                onDismissRequest
            )
        )
    }

    protected fun dismissBasicDialog() {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.dismissBasicDialog()
    }

    protected fun showAlertDialog(
        @StringRes contentText: Int,
        @StringRes leftBtnText: Int? = null,
        @StringRes rightBtnText: Int = R.string.skim_ui_confirm,
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        showAlertDialog(
            getString(contentText),
            leftBtnText?.let { getString(it) },
            getString(rightBtnText),
            onDismissRequest
        )
    }

    protected fun showAlertDialog(
        contentText: String,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        showAlertDialog(
            buildAnnotatedString {
                append(contentText)
            },
            leftBtnText,
            rightBtnText,
            onDismissRequest
        )
    }

    protected fun showAlertDialog(
        contentText: AnnotatedString,
        leftBtnText: String? = null,
        rightBtnText: String = getString(R.string.skim_ui_confirm),
        onDismissRequest: (state: PopupState) -> Unit
    ) {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.showAlertDialog(
            CommonDialogData(
                "",
                contentText,
                leftBtnText,
                rightBtnText,
                onDismissRequest
            )
        )
    }

    protected fun dismissAlertDialog() {
        val baseActivityInterface = activity as? BaseActivityInterface ?: return
        baseActivityInterface.dismissAlertDialog()
    }

    protected fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)?.let { navigate(destination) }
    }

    protected fun terminateApplication() {
        (activity as BaseActivityInterface).quitApplication()
    }


//    @SuppressLint("SimpleDateFormat")
//    protected fun showDatePicker(maxDate: Date? = null, currentDate: String?, onDateChangeListener: (Date) -> Unit) {
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
//                else -> {}
//            }
//        }
//
//        val dateFormat = SimpleDateFormat("yyyyMMdd")
//        val date: Date = currentDate?.let { dateFormat.parse(it) } ?: Date()
//
//        val year = SimpleDateFormat("yyyy").format(date).toInt()
//        val month = SimpleDateFormat("MM").format(date).toInt() - 1
//        val day = SimpleDateFormat("dd").format(date).toInt()
//
//        navigate(
//            DatePickerDialogFragmentDirections.actionGlobalDatePickerDialogFragment(year = year, month = month, day = day, maxDate = maxDate)
//        )
//    }
}