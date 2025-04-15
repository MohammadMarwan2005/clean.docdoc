package com.alaishat.mohammad.clean.docdoc.presentation.feature.home

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
sealed interface HomeUIState {
    data object Loading : HomeUIState
    data class Success(val recommendations: Map<Specialization, List<Doctor>>, val username: String?) : HomeUIState
    data class Error(val error: DomainError) : HomeUIState
}
