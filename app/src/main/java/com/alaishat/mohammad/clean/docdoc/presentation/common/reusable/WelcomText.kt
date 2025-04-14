package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun WelcomeText(title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title, style = CleanDocdocTheme.typography.headlineMedium, color = Seed)
        DocdocBodyText(text = body)
    }
}
