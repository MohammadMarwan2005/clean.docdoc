package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.model.APISuccess
import com.alaishat.mohammad.clean.docdoc.data.model.core.AppointmentD
import com.alaishat.mohammad.clean.docdoc.data.model.toRequest
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.BookAppointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import kotlinx.coroutines.delay
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
class AppointmentsRepoImpl(
    private val apiService: APIService,
    private val safeAPICaller: SafeAPICaller
) : AppointmentsRepo {

    companion object {
        val cacheMap = ConcurrentHashMap<Int, Appointment>()
    }

    override suspend fun getAllUserAppointments(): Resource<List<Appointment>> {
        return safeAPICaller.invoke<APISuccess<List<AppointmentD>>, List<Appointment>>(
            apiCall = {
                apiService.getUserAppointments()
            },
            dataToDomain = {
                it.responseData.reversed().map {
                    val app = it.toDomain()
                    cacheMap[app.id] = app
                    app
                }
            }
        )
    }

    override suspend fun bookAppointment(bookAppointment: BookAppointment): Resource<Appointment> {
        return safeAPICaller.invoke<APISuccess<AppointmentD>, Appointment>(
            apiCall = {
                apiService.bookAppointment(bookAppointment.toRequest())
            },
            dataToDomain = {
                val app = it.responseData.toDomain()
                cacheMap[app.id] = app
                app
            }
        )
    }

    override suspend fun getAppointmentById(appointmentId: Int): Resource<Appointment> {
        return cacheMap[appointmentId]?.let {
            Resource.Success(it)
        } ?: Resource.Error(DomainError.NotFoundError)
    }
}
