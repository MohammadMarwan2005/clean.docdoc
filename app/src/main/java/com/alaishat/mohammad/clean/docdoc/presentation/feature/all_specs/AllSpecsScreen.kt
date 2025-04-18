package com.alaishat.mohammad.clean.docdoc.presentation.feature.all_specs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.TitleWithSeeAllTextButtonRow
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.recommendedDoctorRow
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.LoadingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.DocDocSurface
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllSpecsScreen(
    allSpecsViewModel: AllSpecsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    navigateToDoctorDetails: (doctorId: Int) -> Unit,
) {
    val uiState by allSpecsViewModel.state.collectAsStateWithLifecycle()

    val lazyRowState = rememberLazyListState()

    allSpecsViewModel.selectedIndex.let {
        LaunchedEffect(it) {
            listOf(
                async {
                    lazyRowState.animateScrollToItem(
                        index = it,
                        scrollOffset = (lazyRowState.layoutInfo.viewportSize.width / 2 - 100).toInt() * -1
                    )
                },
            ).awaitAll()
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.all_specializations),
                onLeftIconClick = onNavigateUp,
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            uiState.specializationsUIState.let { specsState ->
                when (specsState) {
                    SpecializationsUIState.Loading -> {
                        LoadingScreen()
                    }

                    is SpecializationsUIState.Error -> {
                        ErrorComposable(
                            modifier = Modifier.fillMaxSize(),
                            error = (specsState).error,
                            onTryAgain = {
                                allSpecsViewModel.fetchAllSpecializations()
                            }
                        )
                    }

                    is SpecializationsUIState.Success -> {
                        uiState.doctorsUIState.let { doctorsState ->
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                stickyHeader(key = "SpecialityRow") {
                                    DoctorSpecialityRow(
                                        lazyRowState = lazyRowState,
                                        showTitle = false,
                                        specs = specsState.specializations,
                                        selected = allSpecsViewModel.selectedIndex,
                                        onClick = { index ->
                                            allSpecsViewModel.changeSelectedIndex(index)
                                        }
                                    )
                                }

                                when (doctorsState) {
                                    DoctorsUIState.Initial -> {
                                        item {
                                            DocdocButton(
                                                onClick = {
                                                    allSpecsViewModel.fetchDoctorsForSelectedSpecialization()
                                                },
                                                isLoading = false,
                                                label = "Load Doctors"
                                            )
                                        }
                                    }

                                    DoctorsUIState.Loading -> {
                                        item { LoadingScreen() }
                                    }

                                    is DoctorsUIState.Error -> {
                                        item {
                                            ErrorComposable(
                                                modifier = Modifier.fillMaxSize(),
                                                error = doctorsState.error,
                                                onTryAgain = {
                                                    allSpecsViewModel.fetchAllSpecializations()
                                                }
                                            )
                                        }
                                    }

                                    is DoctorsUIState.Success -> {
                                        recommendedDoctorRow(
                                            doctorsData = doctorsState.doctors,
                                            onClick = navigateToDoctorDetails
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
fun DoctorSpecialityRow(
    specs: List<Specialization>,
    selected: Int = 0,
    showTitle: Boolean = true,
    onClick: (Int) -> Unit,
    lazyRowState: LazyListState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (showTitle) TitleWithSeeAllTextButtonRow(title = stringResource(R.string.doctor_speciality))
        LazyRow(
            state = lazyRowState,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(specs.size, key = { specs[it].id }) {
                SpecialityComp(
                    name = specs[it].name,
                    selected = selected == it,
                    onClick = {
                        onClick(it)
                    }
                )
            }
        }
    }
}


@Composable
fun SpecialityComp(
    onClick: () -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.doctor),
    name: String = "General",
    selected: Boolean = true,
) {

    val myMod =
        if (selected) Modifier.border(
            width = 4.dp,
            color = Seed,
            shape = CircleShape
        ) else Modifier.padding(8.dp)
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = myMod
                .clip(CircleShape)
                .clickable { onClick() }
                .background(DocDocSurface), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.requiredSize(if (selected) 88.dp else 72.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
