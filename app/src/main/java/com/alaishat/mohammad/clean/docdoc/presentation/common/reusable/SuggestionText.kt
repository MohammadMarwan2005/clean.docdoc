package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun SuggestionText(
    textLabel: String,
    buttonLabel: String, onButtonClick: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = textLabel,
            style = MaterialTheme.typography.bodySmall,
        )

        TextButton(onClick = onButtonClick) {
            Text(
                text = buttonLabel,
                style = MaterialTheme.typography.bodySmall,
                color = Seed
            )
        }
    }
}
