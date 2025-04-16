package com.alaishat.mohammad.clean.docdoc.presentation.feature.all_specs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.SpecializationsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class AllSpecsViewModel @Inject constructor(
    private val specializationsRepo: SpecializationsRepo,
    private val doctorsRepo: DoctorsRepo,
    private val stateDelegate: StateDelegate<AllSpecsUIState>
) : ViewModel(), StateViewModel<AllSpecsUIState> by stateDelegate {

    var selectedIndex by mutableIntStateOf(0)
        private set

    fun changeSelectedIndex(newIndex: Int) {
        selectedIndex = newIndex
        fetchDoctorsForSelectedSpecialization()
    }

    init {
        stateDelegate.setDefaultState(
            AllSpecsUIState(
                doctorsUIState = DoctorsUIState.Initial,
                specializationsUIState = SpecializationsUIState.Loading
            )
        )
        fetchAllSpecializations()
    }

    fun fetchAllSpecializations() {
        stateDelegate.updateState { it.copy(specializationsUIState = SpecializationsUIState.Loading) }
        viewModelScope.launch {
            val response = specializationsRepo.getAllSpecializations()
            Timber.d("response = $response")
            when (response) {
                is Resource.Error -> {
                    stateDelegate.updateState {
                        it.copy(
                            specializationsUIState = SpecializationsUIState.Error(
                                response.error
                            )
                        )
                    }
                }

                is Resource.Success -> {
                    stateDelegate.updateState {
                        it.copy(
                            specializationsUIState = SpecializationsUIState.Success(response.data)
                        )
                    }
                    fetchDoctorsForSelectedSpecialization()
                }
            }
        }
    }

    fun fetchDoctorsForSelectedSpecialization() {
        stateDelegate.updateState { it.copy(doctorsUIState = DoctorsUIState.Loading) }
        viewModelScope.launch {
            state.value.specializationsUIState.let { specsState ->
                when (specsState) {
                    SpecializationsUIState.Loading -> {
                        stateDelegate.updateState {
                            // specs is still loading
                            it.copy(
                                doctorsUIState = DoctorsUIState.Error(
                                    DomainError.CustomError(
                                        message = "Specialization is still loading!",
                                        messageId = R.string.specializations_is_still_loading
                                    )
                                )
                            )
                        }
                    }

                    is SpecializationsUIState.Success -> {
                        if (selectedIndex >= specsState.specializations.size) {
                            stateDelegate.updateState {
                                // specs is still loading
                                it.copy(
                                    doctorsUIState = DoctorsUIState.Error(
                                        DomainError.CustomError(
                                            message = "The selected specialization is invalid!",
                                            messageId = R.string.the_selected_specialization_is_invalid
                                        )
                                    )
                                )
                            }
                        } else {
                            val filteredDoctors =
                                doctorsRepo.getFilteredDoctorsBySpecializationId(specsState.specializations[selectedIndex].id)
                            when (filteredDoctors) {
                                is Resource.Error -> {
                                    stateDelegate.updateState {
                                        it.copy(
                                            doctorsUIState = DoctorsUIState.Error(
                                                error = filteredDoctors.error
                                            )
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    stateDelegate.updateState {
                                        it.copy(
                                            doctorsUIState = DoctorsUIState.Success(
                                                doctors = filteredDoctors.data
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is SpecializationsUIState.Error -> {
                        stateDelegate.updateState {
                            it.copy(
                                doctorsUIState = DoctorsUIState.Error(
                                    specsState.error
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
