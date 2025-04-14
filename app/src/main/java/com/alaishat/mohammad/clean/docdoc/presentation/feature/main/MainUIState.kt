package com.alaishat.mohammad.clean.docdoc.presentation.feature.main

import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
sealed interface MainUIState {
    data object Loading: MainUIState
    data class Success(val wholeGraphFirstRoute: NavigationRoute, val authGraphFirstRoute: NavigationRoute): MainUIState
}
