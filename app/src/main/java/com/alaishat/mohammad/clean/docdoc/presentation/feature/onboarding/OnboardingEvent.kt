package com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
sealed interface OnboardingEvent {
    data object NavigateToRegister: OnboardingEvent
}
