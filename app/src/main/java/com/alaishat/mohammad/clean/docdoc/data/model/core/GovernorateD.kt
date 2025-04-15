package com.alaishat.mohammad.clean.docdoc.data.model.core

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Governorate
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class GovernorateD(
    val id: Int,
    val name: String?
) {
    fun toDomain(): Governorate {
        return Governorate(id = id, name = name)
    }
}
