package com.alaishat.mohammad.clean.docdoc.presentation.feature.all_specs

import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */

data class AllSpecsUIState(
    val doctorsUIState: DoctorsUIState,
    val specializationsUIState: SpecializationsUIState
)

sealed interface DoctorsUIState {
    data object Initial : DoctorsUIState
    data object Loading : DoctorsUIState
    data class Error(val error: DomainError) : DoctorsUIState
    data class Success(val doctors: List<Doctor>) : DoctorsUIState

}

sealed interface SpecializationsUIState {
    data object Loading : SpecializationsUIState
    data class Error(val error: DomainError) : SpecializationsUIState
    data class Success(val specializations: List<Specialization>) : SpecializationsUIState
}
