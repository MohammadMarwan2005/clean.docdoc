package com.alaishat.mohammad.clean.docdoc.domain.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
interface DoctorsRepo {
    suspend fun getRecommendedDoctors(): Resource<Map<Specialization, List<Doctor>>>
    suspend fun getDoctorById(id: Int): Resource<Doctor>
    suspend fun getFilteredDoctorsBySpecializationId(
        specializationId: Int,
    ): Resource<List<Doctor>>

    suspend fun searchForDoctors(query: String): Resource<List<Doctor>>
}
