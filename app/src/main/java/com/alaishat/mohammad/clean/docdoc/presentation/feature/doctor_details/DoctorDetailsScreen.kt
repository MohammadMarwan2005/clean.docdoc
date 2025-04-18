package com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DoctorCard
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.LoadingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Apr/17/2025.
 * Clean DocDoc Project.
 */
@Composable
fun DoctorDetailsScreen(
    doctorDetailsViewModel: DoctorDetailsViewModel,
    onNavigateUp: () -> Unit,
    navigateToScheduleAppointment: (doctorId: Int) -> Unit
) {
    val state by doctorDetailsViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.doctor_information),
                onLeftIconClick = onNavigateUp,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            doctorDetailsViewModel.shareDoctorLink(context = context)
                        }
                    ) {
                        Icon(imageVector = Icons.Outlined.Share, contentDescription = "Share Doctor")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            when (state) {
                DoctorDetailsUIState.Loading -> LoadingScreen()
                is DoctorDetailsUIState.Error -> ErrorComposable(
                    error = (state as DoctorDetailsUIState.Error).error,
                    onTryAgain = {
                        doctorDetailsViewModel.fetchDoctorDetails()
                    })

                is DoctorDetailsUIState.Success -> {
                    DoctorDetailsContent(
                        doctor = (state as DoctorDetailsUIState.Success).data,
                        onNavigateUp = onNavigateUp,
                        navigateToScheduleAppointment = navigateToScheduleAppointment
                    )
                }
            }
        }
    }

}


@Composable
private fun DoctorDetailsContent(
    doctor: Doctor,
    onNavigateUp: () -> Unit,
    navigateToScheduleAppointment: (doctorId: Int) -> Unit
) {
    val pagerState = rememberPagerState { 3 }
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        DocdocButton(
            onClick = {
                navigateToScheduleAppointment(doctor.id)
            },
            isLoading = false,
            label = stringResource(R.string.book_appointment)
        )
    }) { innerPadding ->
        val ignored = innerPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding().plus(16.dp)),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
            ) {
                item {
                    DoctorCard(
                        name = doctor.name,
                        specialization = doctor.specialization.name,
                        city = doctor.city.name,
                        phone = doctor.phone,
                        degree = doctor.degree,
                        gender = doctor.gender,
                        showGenderAndHidPhoneAndCity = true,
                        model = doctor.photo,
                        clickable = false
                    )
                }
                item {
                    Column {
                        TabRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(vertical = 8.dp),
                            selectedTabIndex = pagerState.currentPage,
                            containerColor = MaterialTheme.colorScheme.background,

                            ) {
                            Tab(
                                selected = pagerState.currentPage == 0,
                                unselectedContentColor = Gray,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }
                                }) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = stringResource(R.string.about),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Tab(
                                selected = pagerState.currentPage == 1,
                                unselectedContentColor = Gray,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }) {
                                Text(
                                    text = stringResource(R.string.location),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Tab(
                                selected = pagerState.currentPage == 2,
                                unselectedContentColor = Gray,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(2)
                                    }
                                }) {
                                Text(
                                    text = stringResource(R.string.contacts),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 32.dp),
                            verticalAlignment = Alignment.Top,
                            state = pagerState
                        ) { selectedIndex: Int ->
                            when (selectedIndex) {
                                0 -> {
                                    Column(
                                        modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(
                                            title = stringResource(R.string.description),
                                            info = doctor.description
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.specialization),
                                            info = doctor.specialization.name
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.degree),
                                            info = doctor.degree
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.appointment_price),
                                            info = "${doctor.appointPrice} $"
                                        )
                                    }
                                }

                                1 -> {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(
                                            title = stringResource(R.string.gov_and_city),
                                            info = "${doctor.city.governorate?.name ?: ""}, ${doctor.city.name}"
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.address),
                                            info = doctor.address
                                        )
                                        TitleWithInfo(
                                            title = stringResource(R.string.start_and_end_time),
                                            info = stringResource(
                                                R.string.monday_friday,
                                                doctor.startTime,
                                                doctor.endTime
                                            )
                                        )
                                    }
                                }

                                2 -> {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        TitleWithInfo(
                                            title = stringResource(R.string.phone),
                                            info = doctor.phone
                                        )
                                        TitleWithInfo(
                                            title = stringResource(id = R.string.email),
                                            info = doctor.email
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun TitleWithInfo(title: String, info: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = title, style = CleanDocdocTheme.typography.titleSmall)
        SelectionContainer {
            Text(
                text = info,
                style = MaterialTheme.typography.bodySmall,
                color = Gray,
                fontSize = TextUnit(16f, TextUnitType.Sp),
            )
        }
    }
}
