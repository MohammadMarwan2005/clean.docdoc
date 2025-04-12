package com.alaishat.mohammad.clean.docdoc.domain.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.UserAuthData

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface AuthRepo {
    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String,
    ): Resource<UserAuthData>
}