package com.skim.core.ui.base

import androidx.lifecycle.ViewModel
import com.skim.core.model.BaseLog

open class BaseViewModel : ViewModel() {

    fun onError(throwable: Throwable) {
        BaseLog.eventLogger?.exception(throwable)
    }

}