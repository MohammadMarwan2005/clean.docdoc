package com.alaishat.mohammad.clean.docdoc.data.model

import com.alaishat.mohammad.clean.docdoc.domain.model.UserAuthData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class AuthResponse(
    val message: String,
    @SerializedName("data") val authData: AuthData,
    val status: Boolean,
    val code: Int,
)

@Serializable
data class AuthData(
    val token: String,
    val username: String
) {
    fun toUserAuthData(): UserAuthData = UserAuthData(token = token, username = this@AuthData.username)
}