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
        private const val UNAUTHORIZED_CODE = 401
        private const val UNPROCESSABLE_ENTITY_CODE = 422
        private const val SERVER_ERROR_CODE = 500
        private const val NOT_FOUND_CODE = 404

        val errorsWithCode = listOf(
            UnauthorizedError,
            UnprocessableEntityError,
            ServerError,
            NotFoundError
        )

        fun fromStatusCode(apiErrorAsDomainError: DomainError): DomainError {
            errorsWithCode.forEach {
                if (it.statusCode == apiErrorAsDomainError.statusCode) {
                    return CustomError(
                        message = it.message,
                        messageId = it.messageId,
                        details = it.details ?: apiErrorAsDomainError.details,
                        statusCode = it.statusCode
                    )
                }
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

    data object NotFoundError :
        DomainError(
            message = "Not Found!",
            messageId = R.string.not_found,
            statusCode = NOT_FOUND_CODE
        )

    data object NoInternetError : DomainError(
        message = "No Internet Connection!",
        messageId = R.string.no_internet_connection
    )

    data object UnknownError :
        DomainError(message = "Unknown Error!", messageId = R.string.unknown_error)

    data class CustomError(
        override val message: String,
        override val messageId: Int?,
        override val details: List<String>? = null,
        override val statusCode: Int? = null
    ) :
        DomainError(message = message, messageId = messageId, statusCode = statusCode)
}

