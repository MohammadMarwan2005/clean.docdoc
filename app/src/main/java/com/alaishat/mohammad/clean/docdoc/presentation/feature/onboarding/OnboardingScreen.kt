package com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocBodyText
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocButton
import com.alaishat.mohammad.clean.docdoc.presentation.common.reusable.DocdocTitleText

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@Composable
fun OnboardingScreen(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        onboardingViewModel.viewEvent.collect {
            when (it) {
                OnboardingEvent.NavigateToRegister -> navigateToHome()
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    painter = painterResource(id = R.drawable.on_boarding_top_logo),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(40.dp))
            TheMiddleOnboardingBackground()
            Spacer(modifier = Modifier.height(16.dp))

            DocdocBodyText(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = stringResource(R.string.manage_and_schedule_all_of_your_medical_appointments_easily_with_docdoc_to_get_a_new_experience),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            DocdocButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                onClick = {
                    onboardingViewModel.markOnboarded()
                }) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = stringResource(R.string.get_started)
                )
            }
        }

    }

}

@Composable
fun TheMiddleOnboardingBackground() {
    Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter) {
        Icon(
            modifier = Modifier
                .padding(bottom = 120.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.onboarding_back_logo),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Image(
            painter = painterResource(id = R.drawable.onboarding_doctor_image),
            contentDescription = ""
        )
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.onboarding_linear_effect),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            DocdocTitleText(
                modifier = Modifier.padding(horizontal = 64.dp),
                text = stringResource(R.string.best_doctor_appointment_app),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                minLines = 2,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
