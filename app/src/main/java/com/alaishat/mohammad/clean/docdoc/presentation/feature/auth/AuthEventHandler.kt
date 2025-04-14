package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.ErrorAlertDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */

@Composable
fun AuthEventHandler(
    viewEventsFlow: Flow<AuthEvent>,
    navigateToHome: () -> Unit,
    snackBarState: SnackbarHostState,
) {
    var currentError: DomainError? by rememberSaveable { mutableStateOf(null) }
    var snackBarJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewEventsFlow.collect {
            when (it) {
                AuthEvent.NavigateToHome -> navigateToHome()
                is AuthEvent.ShowError -> {
                    Timber.d("showing message: ${it.error}")
                    currentError = it.error
                }

                is AuthEvent.ShowSnackBar -> {
                    snackBarJob?.cancel()
                    snackBarJob = launch {
                        snackBarState.showSnackbar(
                            message = context.getString(
                                it.messageId
                            )
                        )
                    }
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
