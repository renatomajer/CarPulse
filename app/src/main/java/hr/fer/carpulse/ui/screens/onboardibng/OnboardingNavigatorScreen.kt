package hr.fer.carpulse.ui.screens.onboardibng

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hr.fer.carpulse.viewmodel.OnboardingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun OnboardingNavigatorScreen(
    viewModel: OnboardingViewModel = getViewModel(),
    navigateToHomeScreen: (Boolean) -> Unit
) {
    var screenIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.checkIsOnboarding()
    }

    if (screenIndex == 0) {
        OnboardingCarInfoScreen(
            navigateToDriverInfoScreen = { screenIndex++ },
            viewModel = viewModel
        )
    } else if (screenIndex == 1) {
        OnboardingDriverInfoScreen(
            viewModel = viewModel,
            navigateToCarInfoScreen = { screenIndex-- },
            navigateToDriverSelfInfoScreen = { screenIndex++ })
    } else if (screenIndex == 2) {
        OnboardingDriveSelfInfoScreen(
            viewModel = viewModel,
            navigateToDriverInfoScreen = { screenIndex-- },
            navigateToOnboardingFinishedScreen = { screenIndex++ })
    } else if (screenIndex == 3) {
        OnboardingFinishedScreen(viewModel = viewModel, navigateToHomeScreen = navigateToHomeScreen)
    }

}