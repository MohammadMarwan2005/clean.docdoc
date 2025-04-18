package com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.BookAppointment
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.repo.AppointmentsRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    private val appointmentsRepo: AppointmentsRepo,
    private val stateDelegate: StateDelegate<BookAppointmentUIState>,
    private val eventDelegate: EventDelegate<BookAppointmentEvent>,
    val savedStateHandle: SavedStateHandle,
) : ViewModel(), StateViewModel<BookAppointmentUIState> by stateDelegate,
    EventViewModel<BookAppointmentEvent> by eventDelegate {
    companion object {
        const val DOCTOR_ID_KEY =
            "doctorId" // don't set the key like this: DOCTOR_ID_KEY, it will be invalid...
    }

    val doctorId: Int = savedStateHandle[DOCTOR_ID_KEY] ?: -1

    var notes = mutableStateOf("")

    init {
        stateDelegate.setDefaultState(BookAppointmentUIState())
    }

    fun bookAppointment(
        selectedDateMillis: Long?,
        selectedHour: Int,
        selectedMinute: Int
    ) {
        val selectedLocalDateTime: LocalDateTime? = selectedDateMillis?.let { millis ->
            Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(selectedHour, selectedMinute)
        }

        stateDelegate.updateState { it.copy(isLoading = true) }
        selectedLocalDateTime?.let {
            viewModelScope.launch {
                val response = appointmentsRepo.bookAppointment(
                    BookAppointment(
                        doctorId = doctorId,
                        startTime = selectedLocalDateTime,
                        notes = notes.value
                    )
                )
                when (response) {
                    is Resource.Error -> {
                        eventDelegate.sendEvent(
                            scope = viewModelScope,
                            newEvent = BookAppointmentEvent.ShowError(response.error)
                        )
                    }

                    is Resource.Success -> {
                        eventDelegate.sendEvent(
                            scope = viewModelScope,
                            newEvent = BookAppointmentEvent.NavigateToAppointmentDetailsScreen(
                                response.data
                            )
                        )
                    }
                }
                stateDelegate.updateState { it.copy(isLoading = false) }
            }
        } ?: {
            eventDelegate.sendEvent(
                scope = viewModelScope,
                newEvent = BookAppointmentEvent.ShowError(
                    DomainError.CustomError(
                        message = "Invalid Date or Time",
                        messageId = R.string.invalid_date_or_time
                    )
                )
            )
            stateDelegate.updateState { it.copy(isLoading = false) }
        }
    }
}
