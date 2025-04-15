package com.alaishat.mohammad.clean.docdoc.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.Resource
import com.alaishat.mohammad.clean.docdoc.domain.repo.HomeRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Apr/14/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
    private val stateDelegate: StateDelegate<HomeUIState>,
    private val userLocalDataRepo: UserLocalDataRepo
) : ViewModel(), StateViewModel<HomeUIState> by stateDelegate {

    init {
        stateDelegate.setDefaultState(HomeUIState.Loading)
        fetchHomeData()
    }

    fun fetchHomeData() {
        stateDelegate.updateState { HomeUIState.Loading }
        viewModelScope.launch {
            val response = homeRepo.getRecommendedDoctors()
            val username = userLocalDataRepo.getUsernameAsString()
            when (response) {
                is Resource.Error -> {
                    stateDelegate.updateState { HomeUIState.Error(response.error) }
                }
                is Resource.Success -> {
                    stateDelegate.updateState { HomeUIState.Success(response.data, username = username) }
                }
            }
        }
    }
}
