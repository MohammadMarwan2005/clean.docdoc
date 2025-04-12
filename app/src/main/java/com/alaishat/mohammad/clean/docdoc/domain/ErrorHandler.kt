package com.alaishat.mohammad.clean.docdoc.domain

import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface ErrorHandler {
    fun exceptionToDomainErrorMapper(
        exception: Throwable,
        defaultError: DomainError? = null
    ): DomainError
}