package com.alaishat.mohammad.clean.docdoc.domain.model.core

import java.time.LocalDateTime

/**
 * Created by Mohammad Al-Aishat on Apr/09/2025.
 * Clean DocDoc Project.
 */
data class Doctor(
    val address: String,
    val appointPrice: Int,
    val city: City,
    val degree: String,
    val description: String,
    val email: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone: String,
    val photo: String,
    val specialization: Specialization,
    val endTime: String,
    val startTime: String
)