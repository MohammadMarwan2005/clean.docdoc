package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.model.AuthResponse
import com.alaishat.mohammad.clean.docdoc.data.model.RegisterRequest
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.UserAuthData
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
class AuthRepoImpl(
    private val apiService: APIService,
    private val safeAPICaller: SafeAPICaller
) : AuthRepo {
    override suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
    ): Resource<UserAuthData> {
        return safeAPICaller.invoke<AuthResponse, UserAuthData>(
            apiCall = {
                apiService.register(
                    RegisterRequest(name = name, email = email, phone = phone, password = password, passwordConfirmation = passwordConfirmation, gender = "0")
                )
            },
            dataToDomain = {
                it.authData.toUserAuthData()
            },
        )
    }
}
