package com.alaishat.mohammad.clean.docdoc.data.repo

import com.alaishat.mohammad.clean.docdoc.data.APIService
import com.alaishat.mohammad.clean.docdoc.data.SafeAPICaller
import com.alaishat.mohammad.clean.docdoc.data.model.APISuccess
import com.alaishat.mohammad.clean.docdoc.data.model.core.CityD
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.City
import com.alaishat.mohammad.clean.docdoc.domain.repo.CityRepo

/**
 * Created by Mohammad Al-Aishat on Apr/20/2025.
 * Clean DocDoc Project.
 */
class CityRepoImpl(
    private val safeAPICaller: SafeAPICaller,
    private val apiService: APIService
) : CityRepo {
    override suspend fun getAllCities(): Resource<List<City>> {
        return safeAPICaller.invoke<APISuccess<List<CityD>>, List<City>>(
            apiCall = {
                apiService.getAllCities()
            },
            dataToDomain = {
                it.responseData.map { it.toDomain() }
            }
        )
    }
}
