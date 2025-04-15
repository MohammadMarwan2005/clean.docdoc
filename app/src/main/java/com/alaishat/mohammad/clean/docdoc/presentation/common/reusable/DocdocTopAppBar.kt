package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocdocTheme

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@Composable
fun DocdocTopAppBar(
    navController: NavHostController?,
    text: String,
    onLeftIconClick: () -> Unit = { navController?.navigateUp() },
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

    middleText: @Composable () -> Unit = {
        Text(text = text, style = CleanDocdocTheme.typography.titleSmall)
    },
    trailingIcon: @Composable () -> Unit = {
        Spacer(modifier = Modifier.width(32.dp))
    }) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        middleText()
        trailingIcon()
    }
}