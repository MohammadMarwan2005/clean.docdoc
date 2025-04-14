package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.presentation.common.ui_helpers.getTranslatedMessage

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun ErrorAlertDialog(
    error: DomainError,
    onDismiss: () -> Unit,
    onGotItClicked: (() -> Unit)? = null,
) {
    AppAlertDialog(
        title = error.getTranslatedMessage(context = LocalContext.current),
        details = error.details,
        isError = true,
        onDismiss = onDismiss,
        onGotItClicked = onGotItClicked
    )
}

@Composable
fun AppAlertDialog(
    title: String,
    details: List<String>?,
    isError: Boolean,
    onDismiss: () -> Unit,
    onGotItClicked: (() -> Unit)? = null,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = if (isError) Icons.Outlined.Info else Icons.Default.Check,
                contentDescription = null,
                tint = if (isError) Color.Red else Color.Green,
                modifier = Modifier.size(48.dp)
            )
        },
        title = { Text(text = title) },
        text = { MessageDetailsList(title = title, details = details, isError = isError) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    onGotItClicked?.invoke()
                }
            ) {
                Text(stringResource(R.string.got_it))
            }
        }
    )
}
