package com.alaishat.mohammad.clean.docdoc.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.login.LoginScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.register.RegisterScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding.OnboardingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.getAuthSharedViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateFromLoginToRegister
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateFromRegisterToLogin
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateToRoute
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.pushReplacement
import timber.log.Timber

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val hasBottomAppBar =
        navController.currentBackStackEntryAsState().value?.destination?.route?.let { currentRoute ->
            NavigationRoute.fromRoute(currentRoute)?.hasBottomNavBar == true
        } == true

    val state by mainViewModel.state.collectAsStateWithLifecycle()


    when (state) {
        MainUIState.Loading -> LoadingScreen()
        is MainUIState.Success -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (hasBottomAppBar) {
                        // todo: bottom app bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.Cyan)
                        )
                    }
                }
            ) { innerPadding ->
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = (state as MainUIState.Success).wholeGraphFirstRoute
                ) {
                    composable<NavigationRoute.HomeRoute> {
                        LogDrawing(it)
                        Button(onClick = {
                            navController.navigateToRoute(NavigationRoute.RegisterRoute)
                        }) {
                            Text("This is Home, go to login")
                        }
                    }
                    composable<NavigationRoute.OnboardingRoute> {
                        OnboardingScreen(
                            navigateToRegister = {
                                navController.navigateToRoute(NavigationRoute.RegisterRoute)
                            }
                        )
                    }
                    composable<NavigationRoute.SpecializationsRoute> {
                        LogDrawing(it)
                        Text("Specs")
                    }
                    navigation<NavigationRoute.AuthRoute>(
                        startDestination = (state as MainUIState.Success).authGraphFirstRoute
                    ) {
                        composable<NavigationRoute.LoginRoute> {
                            LogDrawing(it)
                            LoginScreen(
                                authViewModel = navController.getAuthSharedViewModel(it),
                                navigateToHome = {
                                    navController.pushReplacement(NavigationRoute.HomeRoute)
                                },
                                navigateToRegister = {
                                    navController.navigateFromLoginToRegister()
                                }
                            )
                        }
                        composable<NavigationRoute.RegisterRoute> {
                            LogDrawing(it)
                            RegisterScreen(
                                authViewModel = navController.getAuthSharedViewModel(it),
                                navigateToHome = {
                                    navController.pushReplacement(NavigationRoute.HomeRoute)
                                },
                                navigateToLogin = {
                                    navController.navigateFromRegisterToLogin()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LogDrawing(navBackStackEntry: NavBackStackEntry) {
    LaunchedEffect(navBackStackEntry) {
        Timber.d("drawing ${navBackStackEntry.destination.route?.split(".")?.last()}")
    }
}
