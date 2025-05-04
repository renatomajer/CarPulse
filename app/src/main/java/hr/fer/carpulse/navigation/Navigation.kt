package hr.fer.carpulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.fer.carpulse.ui.screens.ConnectScreen
import hr.fer.carpulse.ui.screens.HomeScreen
import hr.fer.carpulse.ui.screens.SettingsScreen
import hr.fer.carpulse.ui.screens.TripReviewScreen
import hr.fer.carpulse.ui.screens.TripsScreen
import hr.fer.carpulse.ui.screens.onboardibng.OnboardingNavigatorScreen

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
            HomeScreen(navController = navController)
        }

        composable(route = Screens.ConnectScreen.route) {
            ConnectScreen(navController = navController)
        }

        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screens.TripsScreen.route) {
            TripsScreen(navController = navController)
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
    }

}