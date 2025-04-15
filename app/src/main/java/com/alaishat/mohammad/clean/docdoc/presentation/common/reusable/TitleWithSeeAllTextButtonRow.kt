package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */

@Composable
fun TitleWithSeeAllTextButtonRow(title: String) {
    Row(
        Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = CleanDocdocTheme.typography.titleMedium)
        // See All Removed...
    }
}
