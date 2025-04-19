package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo.Companion.GUEST_EMAIL
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo.Companion.GUEST_PASSWORD
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo.Companion._guestTokenFlow
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo.Companion.guestToken
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
class AuthenticationCredentialsRepoImpl(
    private val userLocalDataRepo: UserLocalDataRepo,
    private val authRepo: AuthRepo,
    val coroutineScope: CoroutineScope,
) : AuthenticationCredentialsRepo {

    init {
        authenticate()
    }

    override fun authenticate() {
        if (guestToken == null) {
            coroutineScope.launch {
                val response = authRepo.login(
                    email = GUEST_EMAIL,
                    password = GUEST_PASSWORD
                )
                val token  = when (response) {
                    is Resource.Error -> null
                    is Resource.Success -> response.data.token
                }
                guestToken = token
                _guestTokenFlow.emit(token)
            }
        }
    }

    override suspend fun getATokenAsString(): String? {
        if (isLoggedIn()) {
            return userLocalDataRepo.getTokenAsString()
        }
        else {
            authenticate()
            return guestToken
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return userLocalDataRepo.hasToken()
    }

    override fun isLoggedInFlow(): Flow<Boolean> {
        return userLocalDataRepo.getTokenFlow().map {
            it != null
        }
    }
}