package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import com.alaishat.mohammad.clean.docdoc.domain.model.core.City
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
data class SearchUIState(
    val filtersUIState: SearchFilterUIState,
    val searchResultUIState: SearchResultUIState
)

sealed interface SearchResultUIState {
    data object Initial: SearchResultUIState
    data object Loading: SearchResultUIState
    data class Error(val error: DomainError): SearchResultUIState
    data class Success(val data: List<Doctor>): SearchResultUIState
}
sealed interface SearchFilterUIState {
    data object Loading: SearchFilterUIState
    data class Error(val error: DomainError): SearchFilterUIState
    data class Success(val specs: List<Selectable>, val cities: List<Selectable>): SearchFilterUIState
}