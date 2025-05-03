package hr.fer.carpulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hr.fer.carpulse.ui.screens.ConnectScreen
import hr.fer.carpulse.ui.screens.HomeScreen
import hr.fer.carpulse.ui.screens.SettingsScreen
import hr.fer.carpulse.ui.screens.TripReviewScreen
import hr.fer.carpulse.ui.screens.TripsScreen
import hr.fer.carpulse.ui.screens.UserDataScreen

@Composable
fun Navigation(
    startDestination: String,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = getStartDestination(startDestination)
    ) {

        composable(route = Screens.UserDataScreen.route) {
            UserDataScreen(
                navController = navController,
                isOnboarding = false
            )
        }

        composable(route = Screens.UserDataScreen.route + "/onBoarding") {
            UserDataScreen(
                navController = navController,
                isOnboarding = true
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

fun getStartDestination(startDestination: String): String {
    return if (startDestination == Screens.UserDataScreen.route) {
        Screens.UserDataScreen.route + "/onBoarding"
    } else {
        startDestination
    }
}