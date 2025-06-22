package hr.fer.carpulse.navigation

sealed class Screens(val route: String) {
    object OnboardingNavigatorScreen : Screens("onboarding_navigator_screen")
    object HomeScreen : Screens("home_screen")
    object ConnectDeviceScreen : Screens("connect_device_screen")
    object DrivingHistoryScreen: Screens("driving_history_screen")
    object TripReviewScreen : Screens("trip_review_screen")
    object TalkWithAssistantScreen : Screens("talk_with_assistant_screen")
}
