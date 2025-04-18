package com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTextField
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorAlertDialog
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Gray
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    bookAppointmentViewModel: BookAppointmentViewModel,
    onNavigateUp: () -> Unit,
    navigateToAppointmentDetailsScreen: (appointmentId: Int) -> Unit
) {

    val state by bookAppointmentViewModel.state.collectAsStateWithLifecycle()
    EventHandler(
        events = bookAppointmentViewModel.viewEvent,
        navigateToAppointmentDetailsScreen = navigateToAppointmentDetailsScreen
    )
    val scrollState = rememberScrollState()

    val now = Date()
    val currentTimeInMillis = now.time
    val initialCalendar = Calendar.getInstance(Locale.getDefault()).apply {
        timeInMillis = currentTimeInMillis
        add(Calendar.HOUR_OF_DAY, 1)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = initialCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = initialCalendar.get(Calendar.MINUTE)
    )
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTimeInMillis,
        initialDisplayMode = DisplayMode.Input
    )
    val validationMessageId: Int? = validateTime(
        timePickerState = timePickerState,
        datePickerState = datePickerState,
        currentTimeInMillis = currentTimeInMillis
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.doctor_information),
                onLeftIconClick = onNavigateUp,
            )
        },
        bottomBar = {
            DocdocButton(
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    val selectedHour = timePickerState.hour
                    val selectedMinute = timePickerState.minute

                    bookAppointmentViewModel.bookAppointment(
                        selectedDateMillis = selectedDateMillis,
                        selectedHour = selectedHour,
                        selectedMinute = selectedMinute
                    )
                },
                enabled = validationMessageId == null,
                isLoading = state.isLoading,
                label = stringResource(R.string.book_now),
                )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    timeSelectorUnselectedContainerColor = Gray.copy(alpha = 0.1f),
                    clockDialColor = Gray.copy(alpha = 0.1f)
                ),
                layoutType = TimePickerDefaults.layoutType(),
            )
            validationMessageId?.let {
                Surface(
                    modifier = Modifier,
                    contentColor = MaterialTheme.colorScheme.error
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Default.Info,
                                contentDescription = ""
                            )
                            Text(
                                text = stringResource(R.string.time_is_invalid),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Text(
                            text = stringResource(it)
                        )
                    }
                }

            }

            DocdocTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(min = 100.dp),
                singleLine = false,
                value = bookAppointmentViewModel.notes.value,
                onValueChange = { bookAppointmentViewModel.notes.value = it },
                label = { Text(text = stringResource(R.string.your_notes)) },
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}

@Composable
private fun EventHandler(
    events: Flow<BookAppointmentEvent>,
    navigateToAppointmentDetailsScreen: (appointmentId: Int) -> Unit
) {
    var currentError: DomainError? by rememberSaveable { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        events.collect {
            when (it) {
                is BookAppointmentEvent.NavigateToAppointmentDetailsScreen -> {
                    navigateToAppointmentDetailsScreen(it.appointment.id)
                }

                is BookAppointmentEvent.ShowError -> {
                    currentError = it.error
                }
            }
        }
    }
    currentError?.let {
        ErrorAlertDialog(
            error = it,
            onDismiss = {
                currentError = null
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun validateTime(
    timePickerState: TimePickerState,
    datePickerState: DatePickerState,
    currentTimeInMillis: Long
): Int? {
    val selectedTimeMillis = datePickerState.selectedDateMillis ?: currentTimeInMillis
    val selectedTimeCalendar = Calendar.getInstance().apply {
        timeInMillis = selectedTimeMillis
        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        set(Calendar.MINUTE, timePickerState.minute)
    }
    if (selectedTimeCalendar.timeInMillis < currentTimeInMillis) return R.string.you_can_not_book_an_appointment_in_the_past
    return null
}
