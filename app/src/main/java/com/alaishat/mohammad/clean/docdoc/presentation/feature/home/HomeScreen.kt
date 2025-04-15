package com.alaishat.mohammad.clean.docdoc.presentation.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DoctorCard
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.MyRefreshIndicator
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.TitleWithSeeAllTextButtonRow
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit
) {

    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val config = LocalConfiguration.current
    val cardPortion = if (config.orientation == Configuration.ORIENTATION_PORTRAIT) 0.25f else 0.1f
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing = state is HomeUIState.Loading
    Scaffold(
        topBar = {
            TopHomeBar(
                modifier = Modifier.padding(top = 24.dp)
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            onRefresh = {
                homeViewModel.fetchHomeData()
            },
            indicator = {
                MyRefreshIndicator(
                    isRefreshing = isRefreshing,
                    pullToRefreshState = pullToRefreshState
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (state) {
                    HomeUIState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    is HomeUIState.Error -> {
                        item {
                            ErrorComposable(
                                modifier = Modifier.fillMaxSize(),
                                error = (state as HomeUIState.Error).error,
                                onTryAgain = {
                                    homeViewModel.fetchHomeData()
                                },
                            )
                        }
                    }

                    is HomeUIState.Success -> {
                        val successState = (state as HomeUIState.Success)
                        item {
                            HomeBannerWithAnImage(
                                username = successState.username,
                                cardPortion,
                                onFindNearbyClick = navigateToSearch
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        stickyHeader(key = "TitleWithSeeAllTextButtonRow") {
                            TitleWithSeeAllTextButtonRow(title = stringResource(R.string.recommended_doctors))
                        }

                        recommendedCategorizedDoctors(
                            navigateToDoctor = { /* TODO */ },
                            data = successState.recommendations
                        )
                    }
                }
            }

        }
    }

}


@Composable
fun TopHomeBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .padding(bottom = 16.dp)
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.on_boarding_top_logo),
            contentDescription = "",
            tint = Color.Unspecified
        )
    }
}

@Composable
fun HomeBanner(username: String?, cardPortion: Float, onFindNearbyClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Seed)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(cardPortion),
            painter = painterResource(id = R.drawable.banner_back_pattern3),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(cardPortion)
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = username?.let {
                        stringResource(
                            R.string.welcome_book_and_schedule_with_nearest_doctor,
                            it
                        )
                    } ?: stringResource(
                        R.string.guest_welcome_book_and_schedule_with_nearest_doctor,
                    ),
                    style = CleanDocdocTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
                Button(
                    onClick = onFindNearbyClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Seed
                    )
                ) {
                    Text(text = stringResource(R.string.find_nearby))
                }
            }
        }
    }
}

@Composable
fun HomeBannerWithAnImage(
    username: String?,
    cardPortion: Float,
    onFindNearbyClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxHeight(cardPortion),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column {
            HomeBanner(
                username = username,
                cardPortion = cardPortion,
                onFindNearbyClick = onFindNearbyClick
            )
        }
        Image(
            modifier = Modifier
                .fillMaxHeight(0f)
                .padding(end = 16.dp),
            painter = painterResource(id = R.drawable.doctor_ill_2), contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}


fun LazyListScope.recommendedCategorizedDoctors(
    navigateToDoctor: (doctorId: Int) -> Unit,
    data: Map<Specialization, List<Doctor>>,
) {
    data.forEach { data ->
        item {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = data.key.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        recommendedDoctorRow(
            doctorsData = data.value,
            onClick = { doctorId ->
                navigateToDoctor(doctorId)
            })
    }
}

fun LazyListScope.recommendedDoctorRow(
    doctorsData: List<Doctor> = emptyList(),
    onClick: (doctorId: Int) -> Unit,
) {
    itemsIndexed(doctorsData) { index, doctor ->
        DoctorCard(
            name = doctor.name,
            specialization = doctor.specialization.name,
            city = doctor.city.name,
            model = doctor.photo,
            degree = doctor.degree,
            phone = doctor.phone,
            onClick = {
                onClick(doctor.id)
            }
        )

    }
}
