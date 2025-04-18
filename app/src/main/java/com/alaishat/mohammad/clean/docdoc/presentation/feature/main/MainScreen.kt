package com.alaishat.mohammad.clean.docdoc.presentation.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.alaishat.mohammad.clean.docdoc.R
import com.alaishat.mohammad.clean.docdoc.presentation.feature.all_specs.AllSpecsScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info.AppointmentInfoScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.appointment_info.AppointmentInfoViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.feature.appointments.AllAppointmentsScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.login.LoginScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.auth.register.RegisterScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment.BookAppointmentScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.book_appointment.BookAppointmentViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details.DoctorDetailsScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.doctor_details.DoctorDetailsViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.feature.home.HomeScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.onboarding.OnboardingScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.profile.ProfileScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.search.SearchScreen
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.NavigationRoute
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.getAuthSharedViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateFromLoginToRegister
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateFromRegisterToLogin
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.navigateToRoute
import com.alaishat.mohammad.clean.docdoc.presentation.navigation.pushReplacement
import com.alaishat.mohammad.clean.docdoc.presentation.theme.DarkSeed
import com.alaishat.mohammad.clean.docdoc.presentation.theme.Seed
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
    val backStackState = navController.currentBackStackEntryAsState()
    var showBottomAppBar by rememberSaveable { mutableStateOf(false) }
    fun changeShowBottomAppBar(route: NavigationRoute) {
        showBottomAppBar = route.hasBottomNavBar
    }
//    val showBottomAppBar =
//        backStackState.value?.destination?.route?.let { currentRoute ->
//            NavigationRoute.fromRoute(currentRoute)?.hasBottomNavBar == true
//        } == true

    val currentRoute = backStackState.value?.destination?.route?.let(NavigationRoute::fromRoute)

    val selectedIndex = items.indexOfFirst { it.route == currentRoute }
    val isSearchSelected = currentRoute == NavigationRoute.SearchRoute
    val state by mainViewModel.state.collectAsStateWithLifecycle()


    when (state) {
        MainUIState.Loading -> LoadingScreen()
        is MainUIState.Success -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    if (showBottomAppBar)
                        FloatingActionButton(
                            modifier = Modifier
                                .offset(y = 84.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(28.dp),
                            contentColor = Color.White,
                            containerColor = if (isSearchSelected) DarkSeed else Seed,
                            onClick = {
                                navController.navigateToRoute(NavigationRoute.SearchRoute) {
                                    launchSingleTop = true
                                }
                            }) {
                            Icon(
                                modifier = Modifier.padding(28.dp),
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = ""
                            )
                        }
                },
                floatingActionButtonPosition = FabPosition.Center,
                bottomBar = {
                    if (showBottomAppBar) {
                        AppBottomNavBar(
                            selectedItemIndex = selectedIndex,
                            navItems = items,
                            onItemSelected = {
                                navController.navigateToRoute(it) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            ) { innerPadding ->
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = (state as MainUIState.Success).wholeGraphFirstRoute
                ) {
                    navigation<NavigationRoute.AuthRoute>(
                        startDestination = (state as MainUIState.Success).authGraphFirstRoute
                    ) {
                        composable<NavigationRoute.LoginRoute> {
                            changeShowBottomAppBar(NavigationRoute.LoginRoute)
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
                            changeShowBottomAppBar(NavigationRoute.RegisterRoute)
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
                    composable<NavigationRoute.OnboardingRoute> {
                        changeShowBottomAppBar(NavigationRoute.OnboardingRoute)
                        OnboardingScreen(
                            navigateToRegister = {
                                navController.navigateToRoute(NavigationRoute.RegisterRoute)
                            }
                        )
                    }
                    composable<NavigationRoute.HomeRoute> {
                        changeShowBottomAppBar(NavigationRoute.HomeRoute)
                        LogDrawing(it)
                        HomeScreen(navigateToSearch = {
                            navController.navigateToRoute(NavigationRoute.SearchRoute)
                        }, navigateToDoctor = { doctorId ->
                            navController.navigateToRoute(
                                NavigationRoute.DoctorDetailsRoute(
                                    doctorId
                                )
                            )
                        })
                    }
                    composable<NavigationRoute.SpecializationsRoute> {
                        changeShowBottomAppBar(NavigationRoute.SpecializationsRoute)
                        LogDrawing(it)
                        AllSpecsScreen(
                            onNavigateUp = {
                                navController.navigateUp()
                            },
                            navigateToDoctorDetails = { doctorId ->
                                navController.navigateToRoute(
                                    NavigationRoute.DoctorDetailsRoute(
                                        doctorId
                                    )
                                )
                            },
                        )
                    }
                    composable<NavigationRoute.SearchRoute> {
                        changeShowBottomAppBar(NavigationRoute.SearchRoute)
                        SearchScreen(
                            navigateToDoctor = {
                                navController.navigateToRoute(NavigationRoute.DoctorDetailsRoute(it))
                            },
                            onNavigateUp = {
                                navController.navigateUp()
                            }
                        )
                    }
                    composable<NavigationRoute.AppointmentsRoute> {
                        changeShowBottomAppBar(NavigationRoute.AppointmentsRoute)
                        AllAppointmentsScreen(onNavigateUp = {
                            navController.navigateUp()
                        }, navigateToAppointmentDetailsScreen = { appointmentId ->
                            navController.navigateToRoute(
                                NavigationRoute.AppointmentInfoRoute(
                                    appointmentId
                                )
                            )
                        }
                        )
                    }
                    composable<NavigationRoute.ProfileRoute> {
                        changeShowBottomAppBar(NavigationRoute.ProfileRoute)
                        ProfileScreen(onLogout = {
                            navController.pushReplacement(NavigationRoute.LoginRoute)
                        }, onBack = {
                            navController.navigateUp()
                        })
                    }
                    composable<NavigationRoute.DoctorDetailsRoute>(
                        deepLinks = listOf(
                            navDeepLink<NavigationRoute.DoctorDetailsRoute>(
                                basePath = NavigationRoute.DOCTOR_URL
                            )
                        ),
                    ) {
                        val doctorDetailsRoute =
                            it.toRoute<NavigationRoute.DoctorDetailsRoute>()
                        changeShowBottomAppBar(doctorDetailsRoute)
                        val doctorDetailsViewModel: DoctorDetailsViewModel = hiltViewModel()
                        doctorDetailsViewModel.savedStateHandle[DoctorDetailsViewModel.DOCTOR_ID_KEY] =
                            doctorDetailsRoute.doctorId

                        DoctorDetailsScreen(
                            doctorDetailsViewModel = doctorDetailsViewModel,
                            onNavigateUp = {
                                navController.navigateUp()
                            },
                            navigateToScheduleAppointment = { doctorId ->
                                navController.navigateToRoute(
                                    NavigationRoute.BookAppointmentRoute(
                                        doctorId
                                    )
                                )
                            }
                        )
                    }
                    composable<NavigationRoute.BookAppointmentRoute> {
                        val bookAppointmentRoute =
                            it.toRoute<NavigationRoute.BookAppointmentRoute>()
                        changeShowBottomAppBar(bookAppointmentRoute)
                        val bookAppointmentViewModel: BookAppointmentViewModel = hiltViewModel()
                        bookAppointmentViewModel.savedStateHandle[BookAppointmentViewModel.DOCTOR_ID_KEY] =
                            bookAppointmentRoute.doctorId

                        BookAppointmentScreen(
                            bookAppointmentViewModel = bookAppointmentViewModel,
                            onNavigateUp = {
                                navController.navigateUp()
                            },
                            navigateToAppointmentDetailsScreen = { appointmentId ->
                                navController.popBackStack(
                                    NavigationRoute.HomeRoute,
                                    inclusive = false
                                )
                                navController.navigateToRoute(
                                    NavigationRoute.AppointmentInfoRoute(
                                        appointmentId = appointmentId,
                                        isJustBooked = true
                                    )
                                )
                            }
                        )
                    }
                    composable<NavigationRoute.AppointmentInfoRoute> {
                        val appointmentInfoRoute =
                            it.toRoute<NavigationRoute.AppointmentInfoRoute>()
                        changeShowBottomAppBar(appointmentInfoRoute)
                        Timber.d(appointmentInfoRoute.toString())
                        val viewModel: AppointmentInfoViewModel = hiltViewModel()
                        viewModel.savedStateHandle[AppointmentInfoViewModel.APPOINTMENT_ID_KEY] =
                            appointmentInfoRoute.appointmentId
                        viewModel.savedStateHandle[AppointmentInfoViewModel.JUST_BOOKED_KEY] =
                            appointmentInfoRoute.isJustBooked

                        AppointmentInfoScreen(
                            appointmentInfoViewModel = viewModel,
                            onNavigateUp = {
                                navController.navigateUp()
                            },
                            popToHome = {
                                navController.popBackStack(
                                    NavigationRoute.HomeRoute,
                                    inclusive = false
                                )
                            },
                            navigateToDoctorDetailsScreen = { doctorId ->
                                navController.navigateToRoute(
                                    NavigationRoute.DoctorDetailsRoute(
                                        doctorId
                                    )
                                )
                            }
                        )
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

val items = listOf(
    DocDocBottomNavBarItem(
        titleId = R.string.home,
        route = NavigationRoute.HomeRoute,
        selectedIcon = R.drawable.ic_selected_home,
        unselectedIcon = R.drawable.ic_unselected_home,
    ),
    DocDocBottomNavBarItem(
        titleId = R.string.specializations,
        route = NavigationRoute.SpecializationsRoute,
        selectedIcon = R.drawable.ic_selected_specs,
        unselectedIcon = R.drawable.ic_unselected_specs,
    ),

    DocDocBottomNavBarItem(
        titleId = R.string.appointments,
        route = NavigationRoute.AppointmentsRoute,
        selectedIcon = R.drawable.ic_selected_calendar,
        unselectedIcon = R.drawable.ic_unselected_calendar,
        hasNews = true,
        badgeCount = 0
    ),
    DocDocBottomNavBarItem(
        titleId = R.string.my_profile,
        route = NavigationRoute.ProfileRoute,
        selectedIcon = R.drawable.ic_selected_profile,
        unselectedIcon = R.drawable.ic_unselected_profile,
    ),
)
