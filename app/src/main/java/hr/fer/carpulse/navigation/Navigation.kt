package hr.fer.carpulse.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.fer.carpulse.ui.screens.ConnectScreen
import hr.fer.carpulse.ui.screens.HomeScreen
import hr.fer.carpulse.ui.screens.MeasurementsScreen
import hr.fer.carpulse.ui.screens.SettingsScreen

@Composable
fun Navigation(
    applicationContext: Context
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController, applicationContext = applicationContext)
        }

        composable(route = Screens.ConnectScreen.route) {
            ConnectScreen(navController = navController, applicationContext = applicationContext)
        }

        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screens.MeasurementsScreen.route) {
            MeasurementsScreen(navController = navController)
        }
    }

}