package com.alaishat.mohammad.clean.docdoc.presentation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.clean.docdoc.domain.repo.AuthenticationCredentialsRepo
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateDelegate
import com.alaishat.mohammad.clean.docdoc.presentation.common.StateViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val userLocalDataRepo: UserLocalDataRepo,
    private val authenticationCredentialsRepo: AuthenticationCredentialsRepo,
    private val stateDelegate: StateDelegate<MainUIState>,
) : ViewModel(), StateViewModel<MainUIState> by stateDelegate {

    val isLoggedInFlow = authenticationCredentialsRepo
        .isLoggedInFlow()

    init {
        stateDelegate.setDefaultState(MainUIState.Loading)
        checkFirstRoute()
    }

    private fun checkFirstRoute() {
        viewModelScope.launch {
            val hasOnboarded: Boolean? = userLocalDataRepo.getHasOnboarded()
            if (hasOnboarded == true) {
                if (true) {
                    // go to Home
                    stateDelegate.updateState {
                        MainUIState.Success(
                            wholeGraphFirstRoute = NavigationRoute.HomeRoute,
                            authGraphFirstRoute = NavigationRoute.RegisterRoute
                        )
                    }
                } else {
                    // go to Login
                    stateDelegate.updateState {
                        MainUIState.Success(
                            wholeGraphFirstRoute = NavigationRoute.AuthRoute,
                            authGraphFirstRoute = NavigationRoute.LoginRoute()
                        )
                    }
                }
            } else {
                // go to onboarding screen:
                stateDelegate.updateState {
                    MainUIState.Success(
                        wholeGraphFirstRoute = NavigationRoute.OnboardingRoute,
                        authGraphFirstRoute = NavigationRoute.RegisterRoute
                    )
                }
            }
        }
    }
}
