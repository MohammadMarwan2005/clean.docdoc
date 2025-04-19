package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTextField
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.SuggestionText
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.WelcomeText
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthEventHandler
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthViewModel

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by authViewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val snackBarState = remember { SnackbarHostState() }
    AuthEventHandler(
        viewEventsFlow = authViewModel.viewEvent,
        navigateToHome = navigateToHome,
        snackBarState = snackBarState
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .imePadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            WelcomeText(
                title = stringResource((if (authViewModel.isRedirected) R.string.login_first else R.string.welcome_back)),
                body = stringResource(if (authViewModel.isRedirected) R.string.please_login_to_continue else R.string.we_re_excited_to_have_you_back_can_t_wait_to_see_what_you_ve_been_up_to_since_you_last_logged_in)
            )
            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.email.text,
                onValueChange = {
                    authViewModel.email = authViewModel.email.copy(text = it, errorMessageId = null)
                },
                label = { Text(text = stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = !(authViewModel.email.isValid),
                errorText = authViewModel.email.errorMessageId?.let { stringResource(it) }
            )
            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.password.text, onValueChange = {
                    authViewModel.password =
                        authViewModel.password.copy(text = it, errorMessageId = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (authViewModel.showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                label = { Text(text = stringResource(R.string.password)) },
                isError = !(authViewModel.password.isValid),
                trailingIcon = {
                    IconButton(onClick = {
                        authViewModel.showPassword = !authViewModel.showPassword
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password_eye),
                            contentDescription = ""
                        )
                    }
                },
                errorText = authViewModel.password.errorMessageId?.let { stringResource(it) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            DocdocButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    authViewModel.login()
                },
                isLoading = state is AuthUIState.Loading,
                label = stringResource(R.string.login)
            )
            SuggestionText(
                textLabel = stringResource(R.string.don_t_have_an_account_yet),
                buttonLabel = stringResource(R.string.create_account),
                onButtonClick = navigateToRegister
            )
        }

    }
}
