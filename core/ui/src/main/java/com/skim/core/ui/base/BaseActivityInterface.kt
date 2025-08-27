package com.skim.core.ui.base

import androidx.compose.runtime.Composable
import com.skim.core.common.state.CommonDialogData


interface BaseActivityInterface {
    fun getViewModel() : BaseActivityViewModel
    fun setPermissions() : Array<String>
    fun networkAvailable(): Boolean
    fun restartApplication()
    fun quitApplication()

    // Dialog
    fun showAlertDialog(dialogData: CommonDialogData)
    fun showBasicDialog(dialogData: CommonDialogData)
    fun showGlobalDialog(dialogData: CommonDialogData)
    fun dismissBasicDialog()
    fun dismissAlertDialog()
    fun dismissGlobalDialog()

    fun showAlertExpiredSession()
    @Composable
    fun ShowBaseDialog(dialogData: CommonDialogData)
    @Composable
    fun ShowAlertDialog(dialogData: CommonDialogData)
    @Composable
    fun ShowGlobalDialog(dialogData: CommonDialogData)
    @Composable
    fun ShowFloatingDebug(message: String)
}