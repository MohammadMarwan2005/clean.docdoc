package com.alaishat.mohammad.clean.docdoc.presentation.feature.appointments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Appointment
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DoctorCard
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.MyRefreshIndicator
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Gray
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllAppointmentsScreen(
    allAppointmentsViewModel: AllAppointmentsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by allAppointmentsViewModel.state.collectAsStateWithLifecycle()
    val isRefreshing = state is AllAppointmentsUIState.Loading
    val pullToRefreshState = rememberPullToRefreshState()
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.my_appointments),
                onLeftIconClick = onNavigateUp,
            )
        },
    ) { innerPadding ->
        PullToRefreshBox(
            state = pullToRefreshState,
            modifier = Modifier
                .padding(innerPadding),
            isRefreshing = isRefreshing,
            onRefresh = {
                allAppointmentsViewModel.fetchAllAppointments()
            },
            indicator = {
                MyRefreshIndicator(
                    isRefreshing = isRefreshing,
                    pullToRefreshState = pullToRefreshState
                )
            }
        ) {
            when (state) {
                is AllAppointmentsUIState.Error -> {
                    ErrorComposable(
                        modifier = Modifier.fillMaxSize(),
                        error = (state as AllAppointmentsUIState.Error).error,
                        onTryAgain = {
                            allAppointmentsViewModel.fetchAllAppointments()
                        })
                }

                AllAppointmentsUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }

                is AllAppointmentsUIState.Success -> {
                    Column {
                        TabRow(
                            modifier = Modifier
                                .fillMaxWidth(),
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
                                    text = stringResource(R.string.pending),
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
                                    text = stringResource(R.string.completed),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.Top,
                            state = pagerState
                        ) { selectedIndex: Int ->
                            when (selectedIndex) {
                                0 -> {
                                    AppointmentsLazyList(
                                        appointments = (state as AllAppointmentsUIState.Success).pendingAppointments
                                    )
                                }

                                1 -> {
                                    AppointmentsLazyList(
                                        appointments = (state as AllAppointmentsUIState.Success).completedAppointments
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

@Composable
fun AppointmentsLazyList(
    appointments: List<Appointment>
) {
    if (appointments.isEmpty())
        Text(
            text = stringResource(R.string.you_have_no_pending_appointments_yet)
        )
    else
        LazyColumn {
            items(appointments, key = { it.id }) {
                val formattedTime = getFormattedDate(
                    startTime = it.appointmentStartTime,
                    endTime = it.appointmentEndTime
                )
                DoctorCardWithDivider(
                    onClick = {
                        // navigateToDoctorDetailsScreen...
                    },
                    name = it.doctor.name,
                    specialization = it.doctor.name,
                    model = it.doctor.photo,
                    time = formattedTime
                )
            }
        }
}

private fun LazyItemScope.getFormattedDate(
    startTime: LocalDateTime,
    endTime: LocalDateTime
): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.ENGLISH)
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    val formattedDate = startTime.format(dateFormatter)
    val formattedStartTime = startTime.format(timeFormatter)
    val formattedEndTime = endTime.format(timeFormatter)
    return "$formattedDate | $formattedStartTime - $formattedEndTime"
}

@Composable
fun DoctorCardWithDivider(
    name: String = "Dr. Jack Sullivan",
    specialization: String = "General",
    onClick: () -> Unit = {},
    model: String = "",
    time: String = "Wed, 17 May | 08:30 AM - 09:00 AM",
) {
    Column(Modifier.padding(vertical = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DoctorCard(
            name = name,
            specialization = specialization,
            onClick = onClick,
            model = model,
            gender = time,
            showGenderAndHidPhoneAndCity = true,
            clickable = true,
        )
    }
    HorizontalDivider()
}
