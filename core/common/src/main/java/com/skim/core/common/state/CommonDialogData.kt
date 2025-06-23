package com.skim.core.common.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

data class CommonDialogData(
    val titleText: String,
    val contentText: AnnotatedString,
    val leftBtnText: String? = null,
    val rightBtnText: String,
    val onDismissRequest: (popupState: PopupState) -> Unit
)

data class CommonAlertData(
    val contentText: String,
    val leftBtnText: String? = null,
    val rightBtnText: String,
    val onDismissRequest: (popupState: PopupState) -> Unit
)

//sealed class AuthDialogData {
//    object None : AuthDialogData()
//    data class Loading(
//        val message: String? = null,
//        val currentMb: Float? = null,
//        val totalMb: Float? = null,
//        val progress: Float? = null,
//        val optionComposable: @Composable (() -> Unit)? = null,
//    ) : AuthDialogData()
//
//    object ShowAuthGuidePopup : AuthDialogData()
//    object ShowOcrFailedPopup : AuthDialogData()
//    data class AuthComplete(val path: String) : AuthDialogData()
//    data class AuthFailedPopup(val message: String) : AuthDialogData()
//}
//
//data class InstructionData(val title: String, val imagePaths: List<String>)
