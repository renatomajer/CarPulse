package hr.fer.carpulse.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object ConnectScreen : Screens("connect_screen")
    object SettingsScreen : Screens("settings_screen")
    object MeasurementsScreen : Screens("measurements_screen")
}
