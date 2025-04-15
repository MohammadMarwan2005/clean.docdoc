package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class AllAppointmentsViewModel @Inject constructor(
    private val appointmentsRepo: AppointmentsRepo,
    private val stateDelegate: StateDelegate<AllAppointmentsUIState>
) : ViewModel(), StateViewModel<AllAppointmentsUIState> by stateDelegate {

    init {
        stateDelegate.setDefaultState(AllAppointmentsUIState.Loading)
        fetchAllAppointments()
    }


    fun fetchAllAppointments() {
        stateDelegate.updateState { AllAppointmentsUIState.Loading }
        viewModelScope.launch {
            val response = appointmentsRepo.getAllUserAppointments()
            when (response) {
                is Resource.Error -> stateDelegate.updateState {
                    AllAppointmentsUIState.Error(
                        response.error
                    )
                }

                is Resource.Success -> {
                    val pending = response.data.filter { it.status == Appointment.STATUS_PENDING }
                    val completed =
                        response.data.filter { it.status == Appointment.STATUS_COMPLETED }
                    stateDelegate.updateState {
                        AllAppointmentsUIState.Success(
                            pendingAppointments = pending,
                            completedAppointments = completed
                        )
                    }
                }
            }
        }
    }
}
