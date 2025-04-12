package com.alaishat.mohammad.clean.docdoc.presentation.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
fun NavHostController.pushReplacement(route: NavigationRoute) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavHostController.navigateToRoute(route: NavigationRoute, navOptions: NavOptions) {
    // todo: create a middleware here for Guest Mode...
    // just check on a isLoggedInFlow, if (isLoggedIn.not() && route.isProtected) redirect to login screen else complete...
    navigate(route = route, navOptions = navOptions)
}
