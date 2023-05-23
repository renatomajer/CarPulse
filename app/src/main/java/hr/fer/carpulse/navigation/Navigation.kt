package hr.fer.carpulse.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.fer.carpulse.ui.screens.*

@Composable
fun Navigation(
    applicationContext: Context,
    startDestination: String
) {
    val navController = rememberNavController()

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
            HomeScreen(navController = navController, applicationContext = applicationContext)
        }

        composable(route = Screens.ConnectScreen.route) {
            ConnectScreen(navController = navController, applicationContext = applicationContext)
        }

        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screens.TripsScreen.route) {
            TripsScreen(navController = navController, context = applicationContext)
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