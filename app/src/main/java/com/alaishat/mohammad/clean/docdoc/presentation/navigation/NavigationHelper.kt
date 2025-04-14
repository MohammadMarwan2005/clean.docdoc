package com.alaishat.mohammad.clean.docdoc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.AuthViewModel

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
fun NavHostController.pushReplacement(route: NavigationRoute) {
    navigateToRoute(route) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateFromRegisterToLogin() {
    navigateToRoute(NavigationRoute.LoginRoute) {
        popUpTo(NavigationRoute.LoginRoute) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateFromLoginToRegister() {
    navigateToRoute(NavigationRoute.RegisterRoute) {
        popUpTo(NavigationRoute.RegisterRoute) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToRoute(
    route: NavigationRoute,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    // todo: create a middleware here for Guest Mode...
    // just check on a isLoggedInFlow, if (isLoggedIn.not() && route.isProtected) redirect to login screen else complete...
    builder?.let {
        navigate(route = route, builder = it)
    } ?: navigate(route = route)
}

/**
 * @return A shared ViewModel for screens [RegisterScreen, and LoginScreen]
 * */
@Composable
fun NavHostController.getAuthSharedViewModel(navBackStackEntry: NavBackStackEntry): AuthViewModel {
    val parentEntry = remember(navBackStackEntry) {
        getBackStackEntry(NavigationRoute.AuthRoute)
    }
    return hiltViewModel(parentEntry)
}
