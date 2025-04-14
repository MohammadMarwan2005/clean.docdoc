package com.alaishat.mohammad.clean.docdoc.presentation.common.ui_helpers

import android.content.Context
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
fun DomainError.getTranslatedMessage(context: Context): String {
    return messageId?.let {
        context.getString(it)
    } ?: message
}
