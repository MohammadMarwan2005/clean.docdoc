package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
sealed interface AppointmentInfoUIState {
    data object Loading: AppointmentInfoUIState
    data class Error(val error: DomainError): AppointmentInfoUIState
    data class Success(val data: Appointment, val justBooked: Boolean): AppointmentInfoUIState
}
