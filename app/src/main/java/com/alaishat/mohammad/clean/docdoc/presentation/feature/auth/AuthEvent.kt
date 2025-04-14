package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth

import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
sealed interface AuthEvent {
    data object NavigateToHome : AuthEvent
    data class ShowError(val error: DomainError) : AuthEvent
    data class ShowSnackBar(val messageId: Int): AuthEvent
}
