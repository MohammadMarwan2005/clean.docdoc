package com.alaishat.mohammad.clean.docdoc.data.model.core

import com.alaishat.mohammad.clean.docdoc.domain.model.core.City
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class CityD(
    @SerializedName("governorate") val governorate: GovernorateD?,
    val id: Int,
    val name: String
) {
    fun toDomain() : City {
        return City(
            governorate = governorate?.toDomain(),
            id = id,
            name = name
        )
    }
}
