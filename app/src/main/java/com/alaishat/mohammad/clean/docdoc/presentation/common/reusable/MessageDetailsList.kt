package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun MessageDetailsList(
    title: String,
    details: List<String>?,
    isError: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        (details ?: listOf(title)).forEach { detail ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(if (isError) Color.Red else Color.Green)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
