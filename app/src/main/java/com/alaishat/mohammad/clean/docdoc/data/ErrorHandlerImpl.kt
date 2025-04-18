package com.alaishat.mohammad.clean.docdoc.data

import com.alaishat.mohammad.clean.docdoc.domain.ErrorHandler
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
class ErrorHandlerImpl : ErrorHandler {
    override fun exceptionToDomainErrorMapper(
        exception: Throwable,
        defaultError: DomainError?
    ): DomainError {
        exception.printStackTrace()
        return when (exception) {
            is IOException -> {
                DomainError.NoInternetError
            }

            is HttpException -> {
                val message: String = defaultError?.message ?: exception.message()
                val details: List<String>? = defaultError?.details
                val code = exception.code()
                val domainError = DomainError.CustomError(
                    message = message,
                    messageId = null,
                    statusCode = code,
                    details = details
                )
                DomainError.fromStatusCode(domainError)
            }

            else -> {
                defaultError ?: exception.message?.let {
                    DomainError.CustomError(message = it, messageId = null)
                } ?: DomainError.UnknownError
            }
        }
    }
}