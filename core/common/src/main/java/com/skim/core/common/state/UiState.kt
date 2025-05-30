package com.skim.core.common.state

import androidx.compose.runtime.Composable

sealed class UiState<out T> {
    object None : UiState<Nothing>()
    data class Loading(
        val message: String? = null,
        val currentMb: Float? = null,
        val totalMb: Float? = null,
        val progress: Float? = null,
        val optionComposable: @Composable (() -> Unit)? = null,
    ) : UiState<Nothing>()

    data class Success<T : Any>(val data: T) : UiState<T>()

    // UiState.Error
    data class Message(
        val message: String = "",
        val throwable: Throwable? = null,
        val code: Int = 0
    ) : UiState<Nothing>()
}