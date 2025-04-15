package com.alaishat.mohammad.clean.docdoc.presentation.feature.profile

import com.alaishat.mohammad.clean.docdoc.domain.model.ProfileData
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
sealed interface ProfileUIState {
    data object Loading : ProfileUIState
    data class Error(val error: DomainError) : ProfileUIState
    data class Success(val profileData: ProfileData): ProfileUIState
}
