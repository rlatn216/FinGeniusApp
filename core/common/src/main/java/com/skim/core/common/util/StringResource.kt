package com.skim.core.common.util

import android.content.Context
import androidx.annotation.StringRes

data class StringResource(
    val str: String? = null,
    @StringRes val res: Int,
    val params: List<Any>? = null,
) {

    fun toString(context: Context): String {
        return if (str.isNullOrBlank()) context.getString(res, params) else str
    }

}