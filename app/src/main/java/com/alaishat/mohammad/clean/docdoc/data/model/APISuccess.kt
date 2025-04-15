package com.alaishat.mohammad.clean.docdoc.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class APISuccess<T>(
    val message: String,
    @SerializedName("data") val responseData : T,
    @SerializedName("code") val statusCode: Int
)
