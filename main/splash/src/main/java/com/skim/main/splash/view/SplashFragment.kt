package com.skim.main.splash.view

import android.os.Bundle
import android.view.View
import androidx.activity.compose.BackHandler
import com.skim.core.common.state.Cancel
import com.skim.core.common.state.Right
import com.skim.core.model.BaseConfig
import com.skim.core.model.BaseLog
import com.skim.core.ui.base.BaseFragment
import com.skim.main.splash.R
import com.skim.main.splash.viewmodel.SplashCheckResult
import com.skim.main.splash.viewmodel.SplashCheckState
import com.skim.main.splash.viewmodel.SplashViewModel

abstract class SplashFragment : BaseFragment() {
    private val splashViewModel: SplashViewModel by lazy {
        getViewModel() as SplashViewModel
    }

    init {
        baseCompose.content = {
            BackHandler(enabled = true, onBack = {
                showAlertDialog(contentText = getString(com.skim.core.ui.R.string.skim_ui_alert_close_app),
                    rightBtnText = getString(com.skim.core.ui.R.string.skim_ui_yes),
                    leftBtnText = getString(com.skim.core.ui.R.string.skim_ui_no),
                    onDismissRequest = {
                        if (it == Right) {
                            terminateApplication()
                        }
                    })
            })
            SplashContentLayout(splashViewModel.showProgressState)
        }

        baseCompose.surface = {
            handleCheckStep()
            handleCheckResult()
        }
    }

    abstract fun onSplashCheckComplete()
    abstract fun onMdmCheckError(errorResponse: Any?)
//    abstract fun moveToAppUpdate(applicationVersion: ApplicationVersion?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.showProgressState = true
        splashViewModel.checkStepState = SplashCheckState.RootingCheck
    }

    private fun handleCheckStep() {
        when (splashViewModel.checkStepState) {
            SplashCheckState.None -> {}
            SplashCheckState.RootingCheck -> {
                splashViewModel.checkRootingStatus(requireContext())
            }

            SplashCheckState.NetworkCheck -> {
                BaseLog.i("2. 네트워크 체크")

                if (availableNetwork()) {
                    splashViewModel.checkStepState = SplashCheckState.MDMCheck
                } else {
                    splashViewModel.checkStepState = SplashCheckState.None
                    showNetworkErrorPopup()
                }
            }

            SplashCheckState.MDMCheck -> {
                if(BaseConfig.USE_MDM) {
                    splashViewModel.checkMdm(requireActivity())
                }else{
                    splashViewModel.checkStepState = SplashCheckState.AppVersionCheck
                }
            }

            SplashCheckState.AppVersionCheck -> {
                splashViewModel.checkApplicationVersion() // 앱버전 체크
            }
        }
    }

    private fun handleCheckResult() {
        when (val dialogState = splashViewModel.checkResult) {
            SplashCheckResult.None -> {}
//            is SplashCheckResult.NeedApplicationUpdate -> {
//                splashViewModel.showProgressState = false
//                moveToAppUpdate(dialogState.applicationVersion)
//            }

            is SplashCheckResult.RootingCheckError -> {
                splashViewModel.checkStepState = SplashCheckState.None
                showRootingErrorPopup()
            }

            is SplashCheckResult.SplashWorkFinish -> {
                splashViewModel.showProgressState = false
                onSplashCheckComplete()
            }

            is SplashCheckResult.MdmCheckError -> {
                onMdmCheckError(dialogState.errorResponse)
                // MDM 은 다른 솔루션과 앱 단에서 결합되는 형태이기 때문에 상태에 대한 관리만 한다.
                // Mdm 에 대한 체크 로직과 Error 핸들링은 앱 단에서 Override 를 통해서 구현해야 한다.
            }
            else -> {}
        }
    }

    private fun showNetworkErrorPopup() {
        showBasicDialog(titleText = getString(com.skim.core.ui.R.string.skim_ui_alert),
            contentText = getString(R.string.skim_splash_alert_network_is_not_connected_message),
            rightBtnText = getString(com.skim.core.ui.R.string.skim_ui_retry),
            onDismissRequest = {
                if (it != Cancel) {
                    splashViewModel.checkStepState = SplashCheckState.NetworkCheck
                } else {
                    terminateApplication()
                }
            })
    }

    private fun showRootingErrorPopup() {
        showBasicDialog(titleText = getString(com.skim.core.ui.R.string.skim_ui_alert),
            contentText = getString(R.string.skim_splash_alert_detect_rooting_message),
            rightBtnText = getString(com.skim.core.ui.R.string.skim_ui_confirm),
            onDismissRequest = {
                terminateApplication()
            })
    }

}