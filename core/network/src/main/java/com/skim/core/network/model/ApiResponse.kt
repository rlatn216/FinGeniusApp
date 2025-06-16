package com.skim.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(val status: Int, val message: String = "", val data: T? = null)