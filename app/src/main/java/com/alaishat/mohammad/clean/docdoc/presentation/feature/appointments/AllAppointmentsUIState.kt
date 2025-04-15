package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointments

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
sealed interface AllAppointmentsUIState {
    data object Loading: AllAppointmentsUIState
    data class Error(val error: DomainError): AllAppointmentsUIState
    data class Success(val pendingAppointments: List<Appointment>, val completedAppointments: List<Appointment>) : AllAppointmentsUIState
}
