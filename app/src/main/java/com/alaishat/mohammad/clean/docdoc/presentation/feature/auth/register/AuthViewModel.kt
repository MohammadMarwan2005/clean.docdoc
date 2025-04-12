package com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo
) : ViewModel() {

    fun register() {
        Log.d(
            "AuthViewModel",
            "Loading..."
        )
        viewModelScope.launch {
            val response = authRepo.register(
                name = "test",
                email = "unusedtestiamsure@sure.com",
                phone = "0998556887",
                password = "password",
                passwordConfirmation = "password",
            )

            val printThis = when (response) {
                is Resource.Error -> response.error
                is Resource.Success -> response.data
            }.toString()

            Log.d(
                "AuthViewModel",
                printThis
            )
        }
    }

}
