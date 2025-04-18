package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@Composable
fun DocdocTopAppBar(
    text: String,
    onLeftIconClick: () -> Unit,
    leadingIcon: @Composable () -> Unit = {
        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onLeftIconClick() },
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "",
            tint = Color.Unspecified
        )
    },
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    middleText: @Composable () -> Unit = {
        Text(text = text, style = CleanDocdocTheme.typography.titleSmall.copy(color = textColor))
    },
    trailingIcon: @Composable () -> Unit = {
        Spacer(modifier = Modifier.width(32.dp))
    },
    backgroundColor: Color = MaterialTheme.colorScheme.background,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        middleText()
        trailingIcon()
    }
}