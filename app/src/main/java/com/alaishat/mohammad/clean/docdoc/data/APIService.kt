package com.alaishat.mohammad.clean.docdoc.data

import com.alaishat.mohammad.clean.docdoc.data.model.APISuccess
import com.alaishat.mohammad.clean.docdoc.data.model.AuthResponse
import com.alaishat.mohammad.clean.docdoc.data.model.BookAppointmentRequest
import com.alaishat.mohammad.clean.docdoc.data.model.HomeResponse
import com.alaishat.mohammad.clean.docdoc.data.model.LoginRequest
import com.alaishat.mohammad.clean.docdoc.data.model.ProfileDataD
import com.alaishat.mohammad.clean.docdoc.data.model.RegisterRequest
import com.alaishat.mohammad.clean.docdoc.data.model.core.AppointmentD
import com.alaishat.mohammad.clean.docdoc.data.model.core.DoctorD
import com.alaishat.mohammad.clean.docdoc.data.model.core.SpecializationD
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface APIService {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginResponse: LoginRequest
    ): Response<AuthResponse>

    @GET("home/index")
    suspend fun getHomeData(): Response<HomeResponse>

    @GET("user/profile")
    suspend fun getUserProfile(): Response<APISuccess<List<ProfileDataD>>>

    @GET("appointment/index")
    suspend fun getUserAppointments(): Response<APISuccess<List<AppointmentD>>>

    @GET("specialization/index")
    suspend fun getAllSpecializations(): Response<APISuccess<List<SpecializationD>>>

    @GET("doctor/doctor-filter")
    suspend fun getFilteredDoctors(
        @Query("specialization") specializationId: Int?,
        @Query("city") cityId: Int? = null
    ): Response<APISuccess<List<DoctorD>>>

    @GET("doctor/show/{id}")
    suspend fun getDoctorById(
        @Path("id") id: Int
    ): Response<APISuccess<DoctorD>>

    @POST("appointment/store")
    suspend fun bookAppointment(
        @Body bookAppointmentRequest: BookAppointmentRequest
    ): Response<APISuccess<AppointmentD>>
}
