package com.alaishat.mohammad.clean.docdoc.domain.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */
interface SpecializationsRepo {
    suspend fun getAllSpecializations(): Resource<List<Specialization>>
}