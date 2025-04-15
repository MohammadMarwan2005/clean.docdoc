package com.alaishat.mohammad.clean.docdoc.data.model.core

import com.alaishat.mohammad.clean.docdoc.data.model.ProfileDataD
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
data class AppointmentD(
    val id: Int,
    val doctor: DoctorD,
    val patient: ProfileDataD,
    val notes: String,
    @SerializedName("appointment_end_time") val appointmentEndTime: String,
    @SerializedName("appointment_price") val appointmentPrice: Int,
    @SerializedName("appointment_time") val appointmentStartTime: String,
    val status: String
) {
    fun toDomain(): Appointment {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy h:mm a", Locale.ENGLISH)

        val parsedStart = try {
            LocalDateTime.parse(appointmentStartTime, formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDateTime.MIN
        }

        val parsedEnd = try {
            LocalDateTime.parse(appointmentEndTime, formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDateTime.MIN
        }
        return Appointment(
            id = id,
            doctor = doctor.toDomain(),
            patient = patient.toDomain(),
            notes = notes,
            appointmentPrice = appointmentPrice,
            appointmentStartTime = parsedStart,
            appointmentEndTime = parsedEnd,
            status = status
        )
    }
}
