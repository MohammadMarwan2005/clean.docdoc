package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.recommendedDoctorRow
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.LoadingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.BottomNavBarContainerColor
import com.alaishat.mohammad.clean.docdoc.presentation.theme.DocDocUnselectedChipContainer
import com.alaishat.mohammad.clean.docdoc.presentation.theme.DocDocUnselectedChipLabel
import com.alaishat.mohammad.clean.docdoc.presentation.theme.SearchBarContainerColor
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Apr/18/2025.
 * Clean DocDoc Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDoctor: (doctorId: Int) -> Unit,
    onNavigateUp: () -> Unit
) {
    val searchUIState: SearchUIState by viewModel.state.collectAsStateWithLifecycle()
    val searchResultUIState: SearchResultUIState = searchUIState.searchResultUIState
    val filterUIState = searchUIState.filtersUIState
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.search),
                onLeftIconClick = onNavigateUp,
            )
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = BottomNavBarContainerColor,
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                FilterRows(
                    filterUIState = filterUIState,
                    onTryAgain = {
                        viewModel.fetchFilters()
                    },
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchBar(
                    query = query,
                    onQueryChanged = {
                        viewModel.onStateFlowChange(it)
                    },
                    onDoneClicked = {
                        viewModel.search()
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                coroutineScope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter_results),
                                contentDescription = ""
                            )
                        }
                    }
                )
            }

            CustomFilterRows(
                filterUIState = filterUIState,
                onTryAgain = {
                    viewModel.fetchFilters()
                }
            ) { successUIState: SearchFilterUIState.Success ->
                val items: List<Selectable> =
                    successUIState.specs.filter { it.isSelected } + successUIState.cities.filter { it.isSelected }
                FilterRow(
                    items = items
                )
            }

            when (searchResultUIState) {
                is SearchResultUIState.Error -> ErrorComposable(
                    modifier = Modifier.fillMaxSize(),
                    error = searchResultUIState.error,
                    onTryAgain = {
                        viewModel.search()
                    })

                SearchResultUIState.Initial -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.search_for_doctors),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                SearchResultUIState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
                        LoadingScreen()
                    }
                }

                is SearchResultUIState.Success -> {
                    val success = searchResultUIState
                    LazyColumn(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        with(success.data) {
                            item {
                                Text(
                                    text = stringResource(R.string.results_found, this@with.size),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            recommendedDoctorRow(doctorsData = this, onClick = navigateToDoctor)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onDoneClicked: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search_dot),
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(),
        placeholder = {
            Text(text = placeholder)
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = SearchBarContainerColor,
            focusedContainerColor = SearchBarContainerColor,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }, keyboardActions = KeyboardActions(
            onDone = {
                onDoneClicked()
            }
        ),
        trailingIcon = trailingIcon
    )
}


@Composable
fun FilterRow(
    items: List<Selectable>,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { selectable -> selectable.key }) { selectable ->
            FilterChip(
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(
                        durationMillis = 100
                    )
                ),
                selected = selectable.isSelected, onClick = {
                    selectable.onClick()
                },
                trailingIcon = {
                    if (selectable.isSelected)
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null
                        )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),
                        text = selectable.label
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = DocDocUnselectedChipContainer,
                    labelColor = DocDocUnselectedChipLabel,
                    selectedContainerColor = Seed,
                    selectedLabelColor = Color.White,
                    iconColor = Color.White,
                    selectedTrailingIconColor = Color.White,
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Transparent,
                    selected = selectable.isSelected,
                    enabled = true,
                    borderWidth = 0.dp,
                    selectedBorderWidth = 0.dp
                )
            )
        }
    }
}

@Composable
fun ColumnScope.FilterRows(
    filterUIState: SearchFilterUIState,
    onTryAgain: () -> Unit,
) {
    CustomFilterRows(
        filterUIState = filterUIState,
        onTryAgain = onTryAgain
    ) { successState ->
        Text(
            text = stringResource(R.string.speciality),
            style = MaterialTheme.typography.titleMedium
        )
        FilterRow(
            items = successState.specs,
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.city),
            style = MaterialTheme.typography.titleMedium
        )
        FilterRow(
            items = successState.cities,
        )
    }
}

@Composable
fun ColumnScope.CustomFilterRows(
    filterUIState: SearchFilterUIState,
    onTryAgain: () -> Unit,
    successContent: @Composable (SearchFilterUIState.Success) -> Unit
) {
    when (filterUIState) {
        is SearchFilterUIState.Error -> {
            ErrorComposable(
                modifier = Modifier.fillMaxWidth(),
                error = filterUIState.error,
                onTryAgain = onTryAgain
            )
        }

        SearchFilterUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is SearchFilterUIState.Success -> {
            successContent(filterUIState)
        }
    }
}
