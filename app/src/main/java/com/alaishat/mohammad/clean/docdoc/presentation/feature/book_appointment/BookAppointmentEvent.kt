package com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
sealed interface BookAppointmentEvent {
    data class NavigateToAppointmentDetailsScreen(val appointment: Appointment) :
        BookAppointmentEvent

    data class ShowError(val error: DomainError) : BookAppointmentEvent
}
