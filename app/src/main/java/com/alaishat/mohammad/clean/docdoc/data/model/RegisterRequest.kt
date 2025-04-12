package com.alaishat.mohammad.clean.docdoc.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val gender: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
)
