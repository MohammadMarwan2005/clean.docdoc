package com.alaishat.mohammad.clean.docdoc.data.model.core

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class SpecializationD(
    val id: Int,
    val name: String
) {
    fun toDomain(): Specialization {
        return Specialization(
            id = id,
            name = name
        )
    }
}
