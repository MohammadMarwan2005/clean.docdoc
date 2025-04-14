package com.alaishat.mohammad.clean.docdoc.presentation.common
import com.alaishat.mohammad.clean.docdoc.R

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
class StringValidationManager {
    fun validateEmail(email: String): Int? {
        return if (email.isBlank()) R.string.email_cant_be_empty
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) R.string.invalid_email_format
        else null
    }

    fun validatePhone(phone: String): Int? {
        return when {
            phone.isBlank() -> R.string.phone_cannot_be_empty
            !phone.matches(Regex("^09\\d{8}$")) -> R.string.phone_must_start_with_09_and_be_10_digits
            else -> null
        }
    }

    fun validateName(name: String): Int? {
        return if (name.isBlank()) R.string.name_cannot_be_empty else null
    }

    fun validatePassword(password: String): Int? {
        return if (password.length < 6) R.string.password_must_be_at_least_6_characters else null
    }

    fun validatePasswordConfirm(password: String, confirmPassword: String): Int? {
        return if (password != confirmPassword) R.string.passwords_do_not_match else null
    }
}
