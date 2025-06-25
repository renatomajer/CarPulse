package hr.fer.carpulse.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.CarPulseButton
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.avatarImageIds
import hr.fer.carpulse.util.carImageIds
import hr.fer.carpulse.viewmodel.OnboardingViewModel

@Composable
fun OnboardingFinishedScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel,
    navigateToHomeScreen: (Boolean) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppBackgroundColor)
            .padding(horizontal = 48.dp)
            .padding(top = 72.dp, bottom = 19.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.onboarding_screen_finished_title, viewModel.driverName),
            style = title,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .height(164.dp)
                    .width(169.dp),
                painter = painterResource(carImageIds[viewModel.selectedCarImageIndex]),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CarPulseButton(
            onClick = {
                viewModel.saveDriverData()
                viewModel.sendDriverData()

                if (viewModel.isOnboarding) {
                    viewModel.saveOnBoardingState(completed = true)
                }

                navigateToHomeScreen(viewModel.isOnboarding)
            },
            text = stringResource(R.string.onboarding_screen_finished_lets_ride),
            textStyle = title
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .height(164.dp)
                    .width(169.dp),
                painter = painterResource(avatarImageIds[viewModel.selectedAvatarImageIndex]),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = viewModel.driverData.collectAsState().value.vehicleType + "!",
            style = title,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}