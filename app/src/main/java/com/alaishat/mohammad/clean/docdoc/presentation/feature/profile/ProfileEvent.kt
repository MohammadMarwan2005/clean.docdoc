package com.alaishat.mohammad.clean.docdoc.presentation.feature.profile

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
sealed interface ProfileEvent {
    data object NavigateToLogin: ProfileEvent
}