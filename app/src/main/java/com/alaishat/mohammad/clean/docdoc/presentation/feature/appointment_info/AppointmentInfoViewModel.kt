package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class AppointmentInfoViewModel @Inject constructor(
    private val appointmentsRepo: AppointmentsRepo,
    private val stateDelegate: StateDelegate<AppointmentInfoUIState>,
    val savedStateHandle: SavedStateHandle,
) : ViewModel(), StateViewModel<AppointmentInfoUIState> by stateDelegate {
    companion object {
        const val APPOINTMENT_ID_KEY = "appointment_id"
        const val JUST_BOOKED_KEY = "just_booked"
    }

    val appointmentId: Int
        get() = savedStateHandle[APPOINTMENT_ID_KEY] ?: -1

    val justBooked: Boolean
        get() = savedStateHandle[JUST_BOOKED_KEY] ?: false

    init {
        stateDelegate.setDefaultState(AppointmentInfoUIState.Loading)
        fetchAppointment()
    }

    fun fetchAppointment() {
        stateDelegate.updateState { AppointmentInfoUIState.Loading }
        viewModelScope.launch {
            delay(500)
            val response = appointmentsRepo.getAppointmentById(appointmentId = appointmentId)
            when (response) {
                is Resource.Error -> stateDelegate.updateState {
                    AppointmentInfoUIState.Error(
                        response.error
                    )
                }

                is Resource.Success -> stateDelegate.updateState {
                    AppointmentInfoUIState.Success(
                        data = response.data,
                        justBooked = justBooked
                    )
                }
            }
        }
    }
}
