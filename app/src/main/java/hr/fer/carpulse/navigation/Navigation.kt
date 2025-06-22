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
import hr.fer.carpulse.ui.screens.TalkWithAssistantScreen
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
                navigateToConnectScreen = { navController.navigate(Screens.ConnectDeviceScreen.route) },
                navigateToDrivingHistoryScreen = { navController.navigate(Screens.DrivingHistoryScreen.route) },
                navigateToEditProfileScreen = { navController.navigate(Screens.OnboardingNavigatorScreen.route) },
                navigateToStatistics = {
                    // TODO: add navigation
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
                navigateToTripDetails = { tripUUID: String ->
                    //TODO: navigate to trip details
                }
            )
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
    }

}