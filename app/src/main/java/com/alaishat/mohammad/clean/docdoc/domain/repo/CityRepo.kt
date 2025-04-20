package com.alaishat.mohammad.clean.docdoc.domain.repo

import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.City

/**
 * Created by Mohammad Al-Aishat on Apr/20/2025.
 * Clean DocDoc Project.
 */
interface CityRepo {
    suspend fun getAllCities() : Resource<List<City>>
}
