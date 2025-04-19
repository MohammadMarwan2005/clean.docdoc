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
fun NavHostController.pushReplacement(
    route: NavigationRoute, isLoggedIn: Boolean,
    ) {
    navigateToRoute(route = route, isLoggedIn = isLoggedIn) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateFromRegisterToLogin(
    isLoggedIn: Boolean,
) {
    val route = NavigationRoute.LoginRoute()
    navigateToRoute(route = route, isLoggedIn = isLoggedIn) {
        popUpTo(route) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateFromLoginToRegister(
    isLoggedIn: Boolean,
) {
    navigateToRoute(route = NavigationRoute.RegisterRoute, isLoggedIn = isLoggedIn) {
        popUpTo(NavigationRoute.RegisterRoute) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToRoute(
    route: NavigationRoute,
    isLoggedIn: Boolean,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val isProtected = route.isProtected
    if (isProtected && !isLoggedIn) {
        navigate(NavigationRoute.LoginRoute(isRedirected = true))
        return
    }
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
