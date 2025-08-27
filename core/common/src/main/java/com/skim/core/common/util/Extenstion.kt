package com.skim.core.common.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// fragment 화면 전환 및 데이터 callback 관련 소스

sealed class FragmentRequest<T>(val key: String, val resultType: Class<out T>) {
//    object UpdateDocuments : FragmentRequest<Unit>("update_documents", Unit::class.java)
//    object AuthCellPhone : FragmentRequest<Unit>("auth_cell_phone", Unit::class.java)
//    object OcrCamera : FragmentRequest<String>("ocr_camera", String::class.java)
//    object NormalAuthCamera : FragmentRequest<Unit>("normal_camera", Unit::class.java)
//    object DocCamera : FragmentRequest<Unit>("doc_camera", Unit::class.java)
//    object PreviewDoc : FragmentRequest<Int>("preview_doc", Int::class.java)
    object DatePicker : FragmentRequest<String>("date_picker", String::class.java)
//    object SealCamera : FragmentRequest<ByteArray>("seal_camera", ByteArray::class.java)
//    object QRResult : FragmentRequest<Boolean>("qr_result", Boolean::class.java)
//    object Upload : FragmentRequest<Boolean>("upload", Boolean::class.java)
    object Unknown : FragmentRequest<String>("unknown", String::class.java)

    fun fromKey(key: String): Boolean {
        return this.key == key
    }
}

sealed class FragmentResult<T> {
    companion object {
        const val OK = "OK"
        const val CANCEL = "CANCEL"
        const val ERROR = "ERROR"
    }

    class Cancel<T> : FragmentResult<T>()
    data class OK<T>(val data: T?) : FragmentResult<T>()
    data class Error<T>(val message: String) : FragmentResult<T>()
}

inline fun <reified T : Any> Fragment.setFragmentResultListener(
    request: FragmentRequest<T>,
    crossinline listener: ((result: FragmentResult<T>) -> Unit) // crossinline은 listener 내부의 non-local return(내부꺼만 return)
) {
    parentFragmentManager.setFragmentResultListener( //  request.key : 고유 식별자
        request.key, this
    ) fragmentResultListener@{ requestKey, bundle ->

        if (request.key != requestKey) return@fragmentResultListener

        listener(bundleToResult(bundle, T::class.java))
    }
}

inline fun <reified T> bundleToResult(bundle: Bundle, classOfT: Class<T>): FragmentResult<T> {
    return if (bundle.containsKey(FragmentResult.CANCEL)) {
        FragmentResult.Cancel()
    } else if (bundle.containsKey(FragmentResult.ERROR)) {
        FragmentResult.Error(bundle.getString(FragmentResult.ERROR, "unknown"))
    } else {
        val data = bundle.getValue(classOfT)
        FragmentResult.OK(data)
    }
}

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

inline fun <reified T> Bundle.getValue(classOfT: Class<T>): T? {
    val data = getString(FragmentResult.OK) ?: return null
    return if (classOfT.isAssignableFrom(String::class.java)) classOfT.cast(data) else json.decodeFromString(data)
}

inline fun <reified T>Bundle.putValue(key: String, data: T?) {
    if (data is String) {
        putString(key, data)
    } else {
        if (data == null || data is Unit) {
            putString(key, null)
        } else {
            putString(key, json.encodeToString(data))
        }
    }
}


inline fun <reified T> Fragment.setFragmentResult(request: FragmentRequest<T>, result: FragmentResult<T>) {
    val bundle = Bundle()

    when (result) {
        is FragmentResult.Cancel -> bundle.putByte(FragmentResult.CANCEL, 1)
        is FragmentResult.Error -> bundle.putString(FragmentResult.ERROR, result.message)
        is FragmentResult.OK<T> -> {
            bundle.putValue(FragmentResult.OK, result.data)
        }
    }

    parentFragmentManager.setFragmentResult(request.key, bundle)
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }

    throw IllegalArgumentException("no activity")
}