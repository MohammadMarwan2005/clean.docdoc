package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.register

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
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {

    val state by authViewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val snackBarState = remember { SnackbarHostState() }

    AuthEventHandler(
        viewEventsFlow = authViewModel.viewEvent,
        navigateToHome = navigateToHome,
        snackBarState = snackBarState,
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
                title = stringResource(R.string.create_account),
                body = stringResource(R.string.sign_up_now_and_start_exploring_all_that_our_app_has_to_offer_we_re_excited_to_welcome_you_to_our_community)
            )
            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.name.text,
                onValueChange = {
                    authViewModel.name = authViewModel.name.copy(text = it, errorMessageId = null)
                },
                label = { Text(text = stringResource(R.string.name)) },
                isError = !authViewModel.name.isValid,
                errorText = authViewModel.name.errorMessageId?.let { stringResource(it) }
            )

            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.email.text,
                onValueChange = {
                    authViewModel.email = authViewModel.email.copy(text = it, errorMessageId = null)
                },
                label = { Text(text = stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = !authViewModel.email.isValid,
                errorText = authViewModel.email.errorMessageId?.let { stringResource(it) }
            )

            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.phone.text,
                onValueChange = {
                    authViewModel.phone = authViewModel.phone.copy(text = it, errorMessageId = null)
                },
                label = { Text(text = stringResource(R.string.phone_number)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = !authViewModel.phone.isValid,
                errorText = authViewModel.phone.errorMessageId?.let { stringResource(it) }
            )

            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.password.text,
                onValueChange = {
                    authViewModel.password =
                        authViewModel.password.copy(text = it, errorMessageId = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (!authViewModel.showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                label = { Text(text = stringResource(R.string.password)) },
                isError = !authViewModel.password.isValid,
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

            DocdocTextField(
                modifier = Modifier.fillMaxWidth(),
                value = authViewModel.passwordConfirmation.text,
                onValueChange = {
                    authViewModel.passwordConfirmation =
                        authViewModel.passwordConfirmation.copy(text = it, errorMessageId = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (!authViewModel.showPasswordConfirm) PasswordVisualTransformation() else VisualTransformation.None,
                label = { Text(text = stringResource(R.string.confirm_password)) },
                isError = !authViewModel.passwordConfirmation.isValid,
                trailingIcon = {
                    IconButton(onClick = {
                        authViewModel.showPasswordConfirm = !authViewModel.showPasswordConfirm
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password_eye),
                            contentDescription = ""
                        )
                    }
                },
                errorText = authViewModel.passwordConfirmation.errorMessageId?.let {
                    stringResource(
                        it
                    )
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            DocdocButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    authViewModel.register()
                },
                isLoading = state is AuthUIState.Loading,
                label = stringResource(R.string.create_account)
            )


            SuggestionText(
                textLabel = stringResource(R.string.already_have_an_account),
                buttonLabel = stringResource(R.string.log_in),
                onButtonClick = navigateToLogin
            )
        }

    }
}
