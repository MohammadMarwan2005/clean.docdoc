package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.model.APISuccess
import com.alaishat.mohammad.clean.docdoc.data.model.HomeResponse
import com.alaishat.mohammad.clean.docdoc.data.model.core.DoctorD
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Doctor
import com.alaishat.mohammad.clean.docdoc.domain.model.core.Specialization
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
class DoctorsRepoImpl(
    private val safeAPICaller: SafeAPICaller,
    private val apiService: APIService
) : DoctorsRepo {
    override suspend fun getRecommendedDoctors(): Resource<Map<Specialization, List<Doctor>>> {
        return safeAPICaller.invoke<HomeResponse, Map<Specialization, List<Doctor>>>(
            apiCall = {
                apiService.getHomeData()
            },
            dataToDomain = {
                it.homeData.associate {
                    Specialization(it.id, it.name) to it.doctors.map { it.toDomain() }
                }
            }
        )
    }

    override suspend fun getFilteredDoctorsBySpecializationId(specializationId: Int): Resource<List<Doctor>> {
        return safeAPICaller.invoke<APISuccess<List<DoctorD>>, List<Doctor>>(
            apiCall = {
                apiService.getFilteredDoctors(specializationId = specializationId)
            },
            dataToDomain = {
                it.responseData.map { it.toDomain() }
            }
        )
    }
}
