package hr.fer.carpulse.navigation

sealed class Screens(val route: String) {
    object UserDataScreen : Screens("user_data_screen")
    object HomeScreen : Screens("home_screen")
    object ConnectScreen : Screens("connect_screen")
    object SettingsScreen : Screens("settings_screen")
    object TripsScreen : Screens("trips_screen")
    object TripReviewScreen : Screens("trip_review_screen")
}
