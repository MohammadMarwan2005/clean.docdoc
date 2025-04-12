package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.register

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Composable
fun TestRegisterScreen(authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()) {
    Button(onClick = {
        authViewModel.register()
    }) {
        Text("Register")
    }
}