package com.alaishat.mohammad.clean.docdoc.data.model.core

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Serializable
data class DoctorD(
    val address: String,
    @SerializedName("appoint_price") val appointPrice: Int,
    val city: CityD,
    val degree: String,
    val description: String,
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone: String,
    val photo: String,
    val specialization: SpecializationD,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("start_time") val startTime: String
) {
    fun toDomain(): Doctor {

        return Doctor(
            address = address,
            appointPrice = appointPrice,
            city = city.toDomain(),
            degree = degree,
            description = description,
            email = email,
            gender = gender,
            id = id,
            name = name,
            phone = phone,
            photo = photo,
            specialization = specialization.toDomain(),
            endTime = endTime,
            startTime = startTime
        )
    }
}
