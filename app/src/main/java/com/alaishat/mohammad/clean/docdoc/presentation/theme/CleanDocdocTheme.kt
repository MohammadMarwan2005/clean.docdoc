package com.alaishat.mohammad.clean.docdoc.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
object CleanDocdocTheme {

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = Typography(
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            titleMedium = MaterialTheme.typography.headlineMedium.copy(
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            ),
            titleSmall = MaterialTheme.typography.headlineMedium.copy(
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        )

}
