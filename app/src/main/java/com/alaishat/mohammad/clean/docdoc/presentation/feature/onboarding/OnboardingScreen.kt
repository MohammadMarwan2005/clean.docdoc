package com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Composable
fun OnboardingScreen(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit
) {

    // todo: Onboarding UI state
    LaunchedEffect(Unit) {
        onboardingViewModel.viewEvent.collect {
            when (it) {
                OnboardingEvent.NavigateToRegister -> navigateToRegister()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Onboarding")
        DocdocButton(
            onClick = {
                onboardingViewModel.markOnboarded()
            },
            isLoading = false,
            label = "Next"
        )
    }
}
