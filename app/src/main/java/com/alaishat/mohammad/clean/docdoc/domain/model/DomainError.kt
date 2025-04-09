package com.alaishat.mohammad.clean.docdoc.domain.model

import com.alaishat.mohammad.clean.docdoc.R

/**
 * Created by Mohammad Al-Aishat on Apr/09/2025.
 * Clean DocDoc Project.
 */
sealed class DomainError(open val message: String, open val messageId: Int?) {
    data object NoInternetError : DomainError(
        message = "No Internet Connection!",
        messageId = R.string.no_internet_connection
    )

    data object UnauthorizedError :
        DomainError(message = "Unauthorized!", messageId = R.string.unauthorized_error)

    data object UnknownError :
        DomainError(message = "Unknown Error!", messageId = R.string.unknown_error)

    data class CustomError(override val message: String, override val messageId: Int?) :
        DomainError(message = message, messageId = messageId)
}
