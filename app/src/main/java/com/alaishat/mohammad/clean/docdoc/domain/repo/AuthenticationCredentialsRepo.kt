package com.alaishat.mohammad.clean.docdoc.domain.repo

import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
interface AuthenticationCredentialsRepo {
    companion object {
        const val GUEST_EMAIL = "alaishat@guest.com"
        const val GUEST_PASSWORD = "alaishat@guest.password"
        var guestToken: String? = null
    }

    fun authenticate()
    /**
     * if(isLoggedIn) -> return the saved token
     * else -> returns the guest token
     *
     * */
    suspend fun getATokenAsString(): String?
    suspend fun isLoggedIn(): Boolean
    fun isLoggedInFlow(): Flow<Boolean>
}
