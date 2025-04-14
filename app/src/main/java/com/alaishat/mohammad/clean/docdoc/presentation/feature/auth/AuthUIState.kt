package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth

import com.alaishat.mohammad.clean.docdoc.domain.model.UserAuthData
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */

sealed interface AuthUIState {
    data object Initial : AuthUIState
    data object Loading : AuthUIState
    data class Error(val error: DomainError) : AuthUIState
    data class Success(val userAuthData: UserAuthData) : AuthUIState
}
