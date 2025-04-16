package com.alaishat.mohammad.clean.docdoc.domain.model.core

import com.alaishat.mohammad.clean.docdoc.R

/**
 * Created by Mohammad Al-Aishat on Apr/09/2025.
 * Clean DocDoc Project.
 */
sealed class DomainError(
    open val message: String,
    open val messageId: Int?,
    open val statusCode: Int? = null,
    open val details: List<String>? = null
) {
    companion object {
        const val UNAUTHORIZED_CODE = 401
        const val UNPROCESSABLE_ENTITY_CODE = 422
        const val SERVER_ERROR_CODE = 500

        val errorsWithCode = listOf(
            UnauthorizedError,
//            UnprocessableEntityError, // we want to get the message because it differ, examples: (email is taken, email is invalid, phone is take, phone is invalid,
            ServerError
        )

        fun fromStatusCode(apiErrorAsDomainError: DomainError): DomainError {
            errorsWithCode.forEach {
                if (it.statusCode == apiErrorAsDomainError.statusCode) return it
            }
            return apiErrorAsDomainError
        }
    }

    data object UnauthorizedError :
        DomainError(
            message = "Unauthorized!",
            messageId = R.string.unauthorized_error,
            statusCode = UNAUTHORIZED_CODE
        )

    data object UnprocessableEntityError :
        DomainError(
            message = "Unprocessable Entity!",
            messageId = R.string.unprocessable_entity_error,
            statusCode = UNPROCESSABLE_ENTITY_CODE
        )

    data object ServerError :
        DomainError(
            message = "Server Error!",
            messageId = R.string.server_error,
            statusCode = SERVER_ERROR_CODE
        )

    data object NoInternetError : DomainError(
        message = "No Internet Connection!",
        messageId = R.string.no_internet_connection
    )

    data object UnknownError :
        DomainError(message = "Unknown Error!", messageId = R.string.unknown_error)

    data class CustomError(override val message: String, override val messageId: Int?, override val details: List<String>? = null,
                           override val statusCode: Int? = null) :
        DomainError(message = message, messageId = messageId, statusCode = statusCode)
}

