package com.alaishat.mohammad.clean.docdoc.data

import com.alaishat.mohammad.clean.docdoc.data.model.AuthResponse
import com.alaishat.mohammad.clean.docdoc.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface APIService {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : Response<AuthResponse>

}