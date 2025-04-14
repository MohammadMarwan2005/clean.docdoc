package com.alaishat.mohammad.clean.docdoc.presentation.common.reusable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alaishat.mohammad.clean.docdoc.presentation.theme.FocusedTextFieldStrokeColor
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Gray
import com.alaishat.mohammad.clean.docdoc.presentation.theme.TextFieldBackgroundColor
import com.alaishat.mohammad.clean.docdoc.presentation.theme.TextFieldErrorStrokeColor
import com.alaishat.mohammad.clean.docdoc.presentation.theme.UnfocusedTextFieldStrokeColor

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
@Composable
fun DocdocTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    errorText: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    val error = TextFieldErrorStrokeColor
    val focused = FocusedTextFieldStrokeColor
    val unfocused = UnfocusedTextFieldStrokeColor

    OutlinedTextField(
        supportingText = errorText?.let { { Text(it) } },
        value = value, onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedLabelColor = focused,
            focusedIndicatorColor = focused,
            cursorColor = focused,
            focusedTrailingIconColor = focused,
            unfocusedLabelColor = Gray,
            unfocusedIndicatorColor = unfocused,
            unfocusedTrailingIconColor = unfocused,
            focusedContainerColor = TextFieldBackgroundColor,
            errorContainerColor = TextFieldBackgroundColor,
            unfocusedContainerColor = Color.White,
            errorCursorColor = error,
            errorLabelColor = error,
            errorIndicatorColor = error,
            errorSupportingTextColor = error,
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(16.dp),
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines
    )
}
