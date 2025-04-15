package com.alaishat.mohammad.clean.docdoc.domain.model.core

import com.alaishat.mohammad.clean.docdoc.domain.model.UserProfileData
import java.time.LocalDateTime
import java.util.Date

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
data class Appointment(
    val id: Int,
    val doctor: Doctor,
    val patient: UserProfileData,
    val notes: String,
    val appointmentPrice: Int,
    val appointmentStartTime: LocalDateTime,
    val appointmentEndTime: LocalDateTime,
    val status: String
) {
    companion object {
        const val STATUS_PENDING =  "pending"
        const val STATUS_COMPLETED =  "completed"
    }
}