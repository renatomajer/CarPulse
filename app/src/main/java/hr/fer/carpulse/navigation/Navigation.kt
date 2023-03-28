package hr.fer.carpulse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.fer.carpulse.ui.screens.ConnectScreen
import hr.fer.carpulse.ui.screens.HomeScreen
import hr.fer.carpulse.ui.screens.MeasurementsScreen
import hr.fer.carpulse.ui.screens.SettingsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screens.ConnectScreen.route) {
            ConnectScreen(navController = navController)
        }

        composable(route = Screens.SettingsScreen.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screens.MeasurementsScreen.route) {
            MeasurementsScreen(navController = navController)
        }
    }

}