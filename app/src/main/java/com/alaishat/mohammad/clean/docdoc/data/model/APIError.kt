package com.alaishat.mohammad.clean.docdoc.data.model

import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class APIError(
    val message : String,
    @SerializedName("data") val details : Map<String, List<String>>,
    @SerializedName("code") val statusCode: Int
) {
    fun toDomainError(): DomainError {
        return DomainError.CustomError(
            message = message,
            messageId = null,
            statusCode = statusCode,
            details = details.mapNotNull { it.value.firstOrNull() }
        )
    }
}
