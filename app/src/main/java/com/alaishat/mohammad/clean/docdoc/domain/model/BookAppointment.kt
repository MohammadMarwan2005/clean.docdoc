package com.alaishat.mohammad.clean.docdoc.domain.model

import java.time.LocalDateTime

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
data class BookAppointment(
    val doctorId: Int,
    val startTime: LocalDateTime,
    val notes: String
)
