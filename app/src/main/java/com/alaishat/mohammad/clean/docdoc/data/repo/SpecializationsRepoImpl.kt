package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.model.APISuccess
import com.alaishat.mohammad.clean.docdoc.data.model.core.SpecializationD
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization
import com.alaishat.mohammad.clean.docdoc.domain.repo.SpecializationsRepo

/**
 * Created by Mohammad Al-Aishat on Apr/16/2025.
 * Clean DocDoc Project.
 */
class SpecializationsRepoImpl(
    private val safeAPICaller: SafeAPICaller,
    private val apiService: APIService,
) : SpecializationsRepo {
    override suspend fun getAllSpecializations(): Resource<List<Specialization>> {
        return safeAPICaller.invoke<APISuccess<List<SpecializationD>>, List<Specialization>>(
            apiCall = {
                apiService.getAllSpecializations()
            },
            dataToDomain = {
                it.responseData.map { it.toDomain() }
            }
        )
    }
}
