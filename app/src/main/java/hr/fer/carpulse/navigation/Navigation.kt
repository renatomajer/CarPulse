package hr.fer.carpulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.fer.carpulse.ui.screens.ConnectDeviceScreen
import hr.fer.carpulse.ui.screens.DrivingHistoryScreen
import hr.fer.carpulse.ui.screens.HomeScreen
import hr.fer.carpulse.ui.screens.OverallStatisticsScreen
import hr.fer.carpulse.ui.screens.TalkWithAssistantScreen
import hr.fer.carpulse.ui.screens.TripDetailsScreen
import hr.fer.carpulse.ui.screens.TripReviewScreen
import hr.fer.carpulse.ui.screens.onboarding.OnboardingNavigatorScreen

@Composable
fun Navigation(
    startDestination: String,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screens.OnboardingNavigatorScreen.route) {
            OnboardingNavigatorScreen(
                navigateToHomeScreen = { isOnboarding ->

                    if (isOnboarding) {
                        navController.navigate(
                            route = Screens.HomeScreen.route,
                            navOptions = NavOptions.Builder().setPopUpTo(
                                route = Screens.OnboardingNavigatorScreen.route,
                                inclusive = true
                            ).build()
                        )

                    } else {
                        navController.popBackStack(
                            route = Screens.HomeScreen.route,
                            inclusive = false
                        )
                    }
                }
            )
        }

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(
                navigateToTripReviewScreen = { tripUUID ->
                    navController.navigate(Screens.TripReviewScreen.route + "/$tripUUID")
                },
                navigateToConnectScreen = {
                    navController.navigate(Screens.ConnectDeviceScreen.route)
                },
                navigateToDrivingHistoryScreen = {
                    navController.navigate(Screens.DrivingHistoryScreen.route)
                },
                navigateToEditProfileScreen = {
                    navController.navigate(Screens.OnboardingNavigatorScreen.route)
                },
                navigateToStatistics = {
                    navController.navigate(Screens.OverallStatisticsScreen.route)
                },
                navigateToConfigureAssistant = {
                    // TODO: add navigation
                },
                navigateToTalkWithAssistant = {
                    navController.navigate(Screens.TalkWithAssistantScreen.route)
                }
            )
        }

        composable(route = Screens.ConnectDeviceScreen.route) {
            ConnectDeviceScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(route = Screens.DrivingHistoryScreen.route) {
            DrivingHistoryScreen(
                onNavigateBack = { navController.popBackStack() },
                navigateToTripDetails = { tripUUID: String, startDate: String, startTime: String ->
                    navController.navigate(
                        Screens.TripDetailsScreen.route + "/$tripUUID/$startDate/$startTime"
                    )
                }
            )
        }

        composable(
            route = Screens.TripDetailsScreen.route + "/{tripUUID}/{startDate}/{startTime}",
            arguments = listOf(
                navArgument(name = "tripUUID") { type = NavType.StringType },
                navArgument(name = "startDate") { type = NavType.StringType },
                navArgument(name = "startTime") { type = NavType.StringType })
        ) { entry ->
            entry.arguments?.let {
                val tripUUID = it.getString("tripUUID")
                val startDate = it.getString("startDate")
                val startTime = it.getString("startTime")

                if(tripUUID != null && startDate != null && startTime != null) {
                    TripDetailsScreen(
                        tripUUID = tripUUID,
                        startDate = startDate,
                        startTime = startTime,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(
            route = Screens.TripReviewScreen.route + "/{tripUUID}",
            arguments = listOf(navArgument(name = "tripUUID") { type = NavType.StringType })
        ) { entry ->
            entry.arguments?.let {
                val tripUUID = it.getString("tripUUID")
                TripReviewScreen(navController = navController, tripUUID = tripUUID)

            }
        }

        composable(route = Screens.TalkWithAssistantScreen.route) {
            TalkWithAssistantScreen(navigateBack = { navController.popBackStack() })
        }

        composable(route = Screens.OverallStatisticsScreen.route) {
            OverallStatisticsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}