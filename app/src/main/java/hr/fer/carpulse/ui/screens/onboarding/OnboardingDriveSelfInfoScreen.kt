package hr.fer.carpulse.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.CarPulseEstimationComponent
import hr.fer.carpulse.ui.components.CarPulseTextField
import hr.fer.carpulse.ui.components.NavigationCarousel
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.subtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.defaultKeyboardActions
import hr.fer.carpulse.viewmodel.OnboardingViewModel

@Composable
fun OnboardingDriveSelfInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel,
    navigateToDriverInfoScreen: () -> Unit,
    navigateToOnboardingFinishedScreen: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardActions = defaultKeyboardActions(focusManager)

    val driverData by viewModel.driverData.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = AppBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .padding(top = 72.dp, bottom = 19.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.onboarding_screen_driver_info_title),
                style = title,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 43.dp)
        ) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_driver_self_info_driving_age),
                        style = subtitle
                    )
                    CarPulseTextField(
                        value = if (driverData.drivingAge == 0) "" else driverData.drivingAge.toString(),
                        onChange = { viewModel.updateDrivingAge(it) },
                        placeholder = "",
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                        keyboardActions = keyboardActions
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                Column(modifier = Modifier.weight(1f)) { }
            }

            Spacer(modifier = Modifier.height(60.dp))

            CarPulseEstimationComponent(
                description = stringResource(R.string.onboarding_screen_driver_self_info_driving_comfortable),
                selectedGrade = if (driverData.comfort == "") 0 else driverData.comfort.toInt(),
                onGradeClick = { grade -> viewModel.updateComfort(grade.toString()) })

            Spacer(modifier = Modifier.height(25.dp))

            CarPulseEstimationComponent(
                description = stringResource(R.string.onboarding_screen_driver_self_info_driving_safe),
                selectedGrade = if (driverData.security == "") 0 else driverData.security.toInt(),
                onGradeClick = { grade -> viewModel.updateSecurity(grade.toString()) })

            Spacer(modifier = Modifier.height(25.dp))

            CarPulseEstimationComponent(
                description = stringResource(R.string.onboarding_screen_driver_self_info_driving_sport),
                selectedGrade = if (driverData.sportsStyle == "") 0 else driverData.sportsStyle.toInt(),
                onGradeClick = { grade -> viewModel.updateSportsStyle(grade.toString()) })

            Spacer(modifier = Modifier.height(25.dp))

            CarPulseEstimationComponent(
                description = stringResource(R.string.onboarding_screen_driver_self_info_driving_economically),
                selectedGrade = if (driverData.fuelEfficiency == "") 0 else driverData.fuelEfficiency.toInt(),
                onGradeClick = { grade -> viewModel.updateFuelEfficiency(grade.toString()) })

            Spacer(modifier = Modifier.height(30.dp))

            NavigationCarousel(
                carouselCount = 3,
                currentCarouselIndex = 2,
                onPreviousButtonClick = navigateToDriverInfoScreen,
                onNextButtonClick = navigateToOnboardingFinishedScreen
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}