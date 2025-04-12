package com.alaishat.mohammad.clean.docdoc.data

import com.alaishat.mohammad.clean.docdoc.data.model.APIError
import com.alaishat.mohammad.clean.docdoc.domain.ErrorHandler
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.google.gson.Gson
import kotlinx.serialization.SerializationException
import retrofit2.Response
import kotlin.jvm.java

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
class SafeAPICaller(
    private val errorHandler: ErrorHandler,
    private val gson: Gson
) {
    suspend operator fun <Data, Domain> invoke(
        apiCall: suspend () -> Response<Data>,
        dataToDomain: (Data) -> Domain,
    ): Resource<Domain> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                Resource.Success(dataToDomain(response.body()!!))
            } else Resource.Error(DomainError.fromStatusCode(getAPIErrorAsDomainError(response = response)))
        } catch (throwable: Throwable) {
            Resource.Error(errorHandler.exceptionToDomainErrorMapper(throwable))
        }
    }

    private fun getAPIErrorAsDomainError(response: Response<*>): DomainError {
        return try {
            val apiError = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
            val domainError = apiError.toDomainError()
            domainError
        } catch (e: SerializationException) {
            errorHandler.exceptionToDomainErrorMapper(e)
        }
    }
}