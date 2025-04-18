package com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/17/2025.
 * Clean DocDoc Project.
 */
sealed interface DoctorDetailsUIState {
    data object Loading : DoctorDetailsUIState
    data class Error(val error: DomainError) : DoctorDetailsUIState
    data class Success(val data: Doctor) : DoctorDetailsUIState
}
