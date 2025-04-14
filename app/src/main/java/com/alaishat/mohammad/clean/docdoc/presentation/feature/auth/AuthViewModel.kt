package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.model.UserAuthData
import com.alaishat.mohammad.clean.docdoc.domain.model.core.DomainError
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.common.StringValidationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val stateDelegate: StateDelegate<AuthUIState>,
    private val eventDelegate: EventDelegate<AuthEvent>,
    private val userLocalDataRepo: UserLocalDataRepo,
    private val validationManager: StringValidationManager,
) : ViewModel(), StateViewModel<AuthUIState> by stateDelegate,
    EventViewModel<AuthEvent> by eventDelegate {

    init {
        stateDelegate.setDefaultState(AuthUIState.Initial)
    }

    var name by mutableStateOf(FormFieldData())
    var email by mutableStateOf(FormFieldData())
    var phone by mutableStateOf(FormFieldData())
    var password by mutableStateOf(FormFieldData())
    var passwordConfirmation by mutableStateOf(FormFieldData())

    var showPassword by mutableStateOf(false)
    var showPasswordConfirm by mutableStateOf(false)

    fun register() {
        if (!validateForm(onlyEmailAndPassword = false)) {
            sendInvalidInputEvent()
            return
        }

        stateDelegate.updateState { AuthUIState.Loading }
        viewModelScope.launch {
            val response = authRepo.register(
                name = name.text,
                email = email.text,
                phone = phone.text,
                password = password.text,
                passwordConfirmation = passwordConfirmation.text,
            )
            handleAuthResponse(response)
        }
    }

    fun login() {
        if (!validateForm(onlyEmailAndPassword = true)) {
            sendInvalidInputEvent()
            return
        }

        stateDelegate.updateState { AuthUIState.Loading }
        viewModelScope.launch {
            val response = authRepo.login(
                email = email.text,
                password = password.text,
            )
            handleAuthResponse(response)
        }
    }


    private fun handleAuthResponse(response: Resource<UserAuthData>) {
        when (response) {
            is Resource.Success -> {
                viewModelScope.launch {
                    userLocalDataRepo.saveToken(response.data.token)
                    stateDelegate.updateState { AuthUIState.Success(response.data) }
                    eventDelegate.sendEvent(
                        scope = viewModelScope,
                        newEvent = AuthEvent.NavigateToHome
                    )
                }
            }

            is Resource.Error -> {
                val error = with(response.error) {
                    if (this is DomainError.UnauthorizedError) DomainError.CustomError(
                        message = message,
                        messageId = R.string.wrong_credentials
                    ) else this
                }

                stateDelegate.updateState { AuthUIState.Error(error) }
                eventDelegate.sendEvent(
                    scope = viewModelScope,
                    newEvent = AuthEvent.ShowError(error)
                )
            }
        }
    }

    private fun sendInvalidInputEvent() {
        eventDelegate.sendEvent(
            scope = viewModelScope,
            newEvent = AuthEvent.ShowSnackBar(messageId = R.string.enter_valid_input_please)
        )
    }

    fun validateForm(onlyEmailAndPassword: Boolean): Boolean {
        val emailError = validationManager.validateEmail(email.text)
        val passwordError = validationManager.validatePassword(password.text)
        email = email.copy(errorMessageId = emailError)
        password = password.copy(errorMessageId = passwordError)

        if (onlyEmailAndPassword) return listOf(emailError, passwordError).all { it == null }

        val nameError = validationManager.validateName(name.text)
        val phoneError = validationManager.validatePhone(phone.text)
        val confirmError =
            validationManager.validatePasswordConfirm(password.text, passwordConfirmation.text)
        name = name.copy(errorMessageId = nameError)
        phone = phone.copy(errorMessageId = phoneError)
        passwordConfirmation = passwordConfirmation.copy(errorMessageId = confirmError)

        return listOf(
            nameError,
            emailError,
            phoneError,
            passwordError,
            confirmError
        ).all { it == null }
    }

    override fun onCleared() {
        Timber.d("Cleared")
        super.onCleared()
    }
}

data class FormFieldData(
    val text: String = "",
    val errorMessageId: Int? = null
) {
    val isValid: Boolean
        get() = errorMessageId == null
}
