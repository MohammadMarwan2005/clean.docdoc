package com.alaishat.mohammad.clean.docdoc.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.EventViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/15/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val stateDelegate: StateDelegate<ProfileUIState>,
    private val eventDelegate: EventDelegate<ProfileEvent>,
    private val userLocalDataRepo: UserLocalDataRepo
) : ViewModel(), StateViewModel<ProfileUIState> by stateDelegate,
    EventViewModel<ProfileEvent> by eventDelegate {

    init {
        stateDelegate.setDefaultState(ProfileUIState.Loading)
        fetchProfile()
    }

    fun fetchProfile() {
        stateDelegate.updateState { ProfileUIState.Loading }
        viewModelScope.launch {
            val response = authRepo.getProfileData()
            when (response) {
                is Resource.Error -> stateDelegate.updateState { ProfileUIState.Error(response.error) }
                is Resource.Success -> stateDelegate.updateState { ProfileUIState.Success(response.data) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userLocalDataRepo.removeUserTokenAndUsername()
            eventDelegate.sendEvent(scope = viewModelScope, newEvent = ProfileEvent.NavigateToLogin)
        }
    }
}
