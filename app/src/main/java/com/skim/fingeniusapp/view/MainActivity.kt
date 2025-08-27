package com.skim.fingeniusapp.view

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skim.core.common.state.CommonDialogData
import com.skim.core.designsystem.theme.SKimTheme
import com.skim.core.model.BaseConfig
import com.skim.core.ui.base.BaseActivityInterface
import com.skim.core.ui.base.BaseActivityUtils
import com.skim.core.ui.base.BaseActivityViewModel
import com.skim.core.ui.base.BaseUiUtil
import com.skim.fingeniusapp.FinApplication
import com.skim.fingeniusapp.util.TouchEventTimeoutAlarmCallback
import com.skim.fingeniusapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseActivityInterface {
    val viewModel: MainViewModel by viewModels()
    private lateinit var baseActivityUtils: BaseActivityUtils

    private var touchEventTimeoutAlarmCallback: TouchEventTimeoutAlarmCallback =
        TouchEventTimeoutAlarmCallback {
            timeOutTouchEvent()
        }

    private val PERMISSIONS: Array<String>
        get() {
            val permissionsList = mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            // SIM 전화번호 추출 : 사용자의 안드로이드 버전에 따라 권한을 다르게 요청
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                permissionsList.add(Manifest.permission.READ_PHONE_NUMBERS)
            } else {
                permissionsList.add(Manifest.permission.READ_PHONE_STATE)
            }

            // 알림 권한
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsList.add(Manifest.permission.POST_NOTIFICATIONS)
            }

            return permissionsList.toTypedArray()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseActivityUtils = BaseActivityUtils(applicationContext, getViewModel())
        baseActivityUtils.processOnCreate(this, setPermissions())
        BaseUiUtil.hideSystemUI(this)


        setContent {
            SKimTheme {

                viewModel.basicDialogData?.let {
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
                        ShowBaseDialog(dialogData = it)
                    }
                }
                viewModel.alertDialogData?.let {
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
                        ShowAlertDialog(dialogData = it)
                    }
                }
                viewModel.globalDialogData?.let {
                    ShowGlobalDialog(dialogData = it)
                }
            }
        }
    }

    private fun timeOutTouchEvent() {
        showAlertExpiredSession()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        baseActivityUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }


    override fun getViewModel(): BaseActivityViewModel = viewModel

    override fun setPermissions(): Array<String> = PERMISSIONS

    override fun networkAvailable(): Boolean = baseActivityUtils.networkAvailable()

    override fun restartApplication() = baseActivityUtils.restartApplication()

    override fun quitApplication() = baseActivityUtils.quitApplication(this)

    override fun showAlertDialog(dialogData: CommonDialogData) =
        baseActivityUtils.showAlertDialog(dialogData = dialogData)

    override fun showBasicDialog(dialogData: CommonDialogData) =
        baseActivityUtils.showBasicDialog(dialogData = dialogData)

    override fun showGlobalDialog(dialogData: CommonDialogData) {
        baseActivityUtils.showGlobalDialog(dialogData = dialogData)
    }

    override fun dismissBasicDialog() = baseActivityUtils.dismissBasicDialog()

    override fun dismissAlertDialog() = baseActivityUtils.dismissAlertDialog()

    override fun dismissGlobalDialog() = baseActivityUtils.dismissGlobalDialog()

    @Composable
    override fun ShowBaseDialog(dialogData: CommonDialogData) =
        baseActivityUtils.ShowBaseDialog(dialogData = dialogData)

    @Composable
    override fun ShowAlertDialog(dialogData: CommonDialogData) =
        baseActivityUtils.ShowAlertDialog(dialogData = dialogData)

    @Composable
    override fun ShowGlobalDialog(dialogData: CommonDialogData) =
        baseActivityUtils.ShowGlobalDialog(dialogData = dialogData)

    override fun showAlertExpiredSession() = baseActivityUtils.showAlertExpiredSession()

    @Composable
    override fun ShowFloatingDebug(message: String) {
        baseActivityUtils.ShowFloatingDebug(message = message)
    }

}