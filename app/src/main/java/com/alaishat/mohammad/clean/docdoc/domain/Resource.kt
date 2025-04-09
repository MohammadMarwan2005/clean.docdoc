package com.alaishat.mohammad.clean.docdoc.domain

import com.alaishat.mohammad.clean.docdoc.domain.model.DomainError

/**
 * Created by Mohammad Al-Aishat on Apr/09/2025.
 * Clean DocDoc Project.
 */
sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val error: DomainError) : Resource<Nothing>()
}