package com.alaishat.mohammad.clean.docdoc.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.DoctorsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val doctorsRepo: DoctorsRepo,
    private val stateDelegate: StateDelegate<HomeUIState>,
    private val userLocalDataRepo: UserLocalDataRepo
) : ViewModel(), StateViewModel<HomeUIState> by stateDelegate {

    companion object {
        private var tryAgain = true
    }

    init {
        stateDelegate.setDefaultState(HomeUIState.Loading)
        fetchHomeData()
    }

    fun fetchHomeData() {
        stateDelegate.updateState { HomeUIState.Loading }
        viewModelScope.launch {
            if (AuthenticationCredentialsRepo.guestToken == null) {
                Timber.d("lets delay a little bit")
                delay(1000)
            } // in the first time, the guest token is still null
            val response = doctorsRepo.getRecommendedDoctors()
            val username = userLocalDataRepo.getUsernameAsString()
            when (response) {
                is Resource.Error -> {
                    Timber.d("Error Occurred ${response.error}")
                    if (tryAgain) {
                        Timber.d("We will try again!")
                        tryAgain = false
                        fetchHomeData()
                        return@launch
                    }
                    stateDelegate.updateState { HomeUIState.Error(response.error) }
                }

                is Resource.Success -> {
                    stateDelegate.updateState {
                        HomeUIState.Success(
                            response.data,
                            username = username
                        )
                    }
                }
            }
            tryAgain = false
        }
    }
}
