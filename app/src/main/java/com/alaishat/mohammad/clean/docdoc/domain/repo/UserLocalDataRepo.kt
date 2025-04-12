package com.alaishat.mohammad.clean.docdoc.domain.repo

import kotlinx.coroutines.flow.Flow

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface UserLocalDataRepo {
    fun getTokenFlow(): Flow<String?>
    fun getTokenAsString(): String?
}