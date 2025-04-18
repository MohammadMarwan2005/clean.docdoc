package com.alaishat.mohammad.clean.docdoc.domain.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.BookAppointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
interface AppointmentsRepo {
    suspend fun getAllUserAppointments(): Resource<List<Appointment>>
    suspend fun bookAppointment(bookAppointment: BookAppointment): Resource<Appointment>
    suspend fun getAppointmentById(appointmentId: Int): Resource<Appointment>
}
