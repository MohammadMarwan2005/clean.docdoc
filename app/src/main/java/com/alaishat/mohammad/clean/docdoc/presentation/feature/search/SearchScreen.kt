package com.alaishat.mohammad.clean.docdoc.presentation.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTopAppBar
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorComposable
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.recommendedDoctorRow
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.LoadingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.SearchBarContainerColor

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
    val state: SearchUIState by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            DocdocTopAppBar(
                text = stringResource(R.string.search),
                onLeftIconClick = onNavigateUp,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(
                query = query,
                onQueryChanged = {
                    viewModel.onStateFlowChange(it)
                },
                onDoneClicked = {
                    viewModel.search()
                },
            )

            // todo later: add cities and specializations filter cards...
            when (state) {
                is SearchUIState.Error -> ErrorComposable(
                    modifier = Modifier.fillMaxSize(),
                    error = (state as SearchUIState.Error).error,
                    onTryAgain = {
                        viewModel.search()
                    })

                SearchUIState.Initial -> {
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

                SearchUIState.Loading -> {
                    LoadingScreen()
                }

                is SearchUIState.Success -> {
                    val success = state as SearchUIState.Success
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
    placeholder: String = stringResource(R.string.search_dot)
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
        )
    )
}
