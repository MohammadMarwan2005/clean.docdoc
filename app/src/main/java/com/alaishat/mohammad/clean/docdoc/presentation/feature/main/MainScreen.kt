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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val hasBottomAppBar = navController.currentBackStackEntryAsState().value?.destination?.route?.let { currentRoute ->
        NavigationRoute.fromRoute(currentRoute)?.hasBottomNavBar == true
    } == true

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (hasBottomAppBar) {
                // todo: bottom app bar
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Cyan))
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavigationRoute.LoginRoute
        ) {
            composable<NavigationRoute.HomeRoute> {
                Button(onClick = {
                    navController.navigate(NavigationRoute.LoginRoute)
                }) {
                    Text("This is Home, go to login")
                }
            }
            composable<NavigationRoute.SpecializationsRoute> {
                Text("Specs")
            }
            composable<NavigationRoute.LoginRoute> {
                Text("Login")
                Button(onClick = {
                    navController.navigate(NavigationRoute.HomeRoute)
                }) {
                    Text("Go to home")
                }
            }
        }
    }
}
