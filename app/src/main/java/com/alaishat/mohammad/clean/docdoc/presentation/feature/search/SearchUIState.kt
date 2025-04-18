package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
sealed interface SearchUIState {
    data object Initial: SearchUIState
    data object Loading: SearchUIState
    data class Error(val error: DomainError): SearchUIState
    data class Success(val data: List<Doctor>): SearchUIState
}
