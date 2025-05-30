package com.skim.core.common.state

sealed interface PopupState
object Cancel : PopupState
object Left : PopupState
object Right : PopupState
