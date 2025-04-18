package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DoctorCard
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.LoadingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Gray

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@Composable
fun AppointmentInfoScreen(
    appointmentInfoViewModel: AppointmentInfoViewModel,
    onNavigateUp: () -> Unit,
    popToHome: () -> Unit,
    navigateToDoctorDetailsScreen: (doctorId: Int) -> Unit,
) {

    val state by appointmentInfoViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.details),
                onLeftIconClick = onNavigateUp,
            )
        },
        bottomBar = {
            DocdocButton(
                onClick = popToHome,
                label = stringResource(R.string.done),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            when (state) {
                is AppointmentInfoUIState.Error -> {
                    ErrorComposable(
                        error = (state as AppointmentInfoUIState.Error).error,
                        onTryAgain = {
                            appointmentInfoViewModel.fetchAppointment()
                        }
                    )
                }

                AppointmentInfoUIState.Loading -> {
                    LoadingScreen()
                }

                is AppointmentInfoUIState.Success -> {
                    val success = state as AppointmentInfoUIState.Success
                    AppointmentInfoContent(
                        appointment = success.data,
                        showConfirmationIcon = success.justBooked,
                        navigateToDoctorDetailsScreen = navigateToDoctorDetailsScreen
                    )
                }
            }
        }
    }
}


@Composable
fun AppointmentInfoContent(
    appointment: Appointment,
    showConfirmationIcon: Boolean,
    navigateToDoctorDetailsScreen: (doctorId: Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (showConfirmationIcon)
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.ic_done_check),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.booking_confirmed),
                    style = CleanDocdocTheme.typography.titleSmall,
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontWeight = FontWeight.Medium,
                )
            }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.booking_information),
            style = CleanDocdocTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_date),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stringResource(R.string.date_and_time),
                    style = CleanDocdocTheme.typography.titleSmall,
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = appointment.startTimeStr,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray,
                )
            }
        }
        HorizontalDivider()
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.doctor_information),
            style = CleanDocdocTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        appointment.doctor.let { doctor ->
            DoctorCard(
                name = doctor.name,
                specialization = doctor.specialization.name,
                city = doctor.city.name,
                phone = doctor.phone,
                degree = doctor.degree,
                gender = doctor.gender,
                showGenderAndHidPhoneAndCity = true,
                model = doctor.photo,
                clickable = !showConfirmationIcon,
                onClick = {
                    navigateToDoctorDetailsScreen(doctor.id)
                }
            )
        }
        HorizontalDivider()
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.your_notes),
            style = CleanDocdocTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = appointment.notes, style = CleanDocdocTheme.typography.bodyMedium,
        )
    }
}
