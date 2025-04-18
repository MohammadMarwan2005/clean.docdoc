package com.alaishat.mohammad.clean.docdoc.data.model

import com.alaishat.mohammad.clean.docdoc.domain.model.UserProfileData
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class ProfileDataD(
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone: String
) {
    fun toDomain(): UserProfileData {
        return UserProfileData(
            id = id,
            email = email,
            name = name,
            phone = phone
        )
    }
}
