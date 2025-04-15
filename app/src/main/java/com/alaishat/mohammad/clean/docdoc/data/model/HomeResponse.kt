package com.alaishat.mohammad.clean.docdoc.data.model

import com.alaishat.mohammad.clean.docdoc.data.model.core.DoctorD
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class HomeResponse(
    val message: String,
    @SerializedName("data") val homeData: List<HomeData>,
    val status: Boolean,
    val code: Int,
)

@Serializable
data class HomeData(
    val id: Int,
    val name: String,
    val doctors: List<DoctorD>,
)