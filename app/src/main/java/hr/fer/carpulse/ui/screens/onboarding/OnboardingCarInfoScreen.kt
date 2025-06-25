package hr.fer.carpulse.ui.screens.onboarding

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.driver.getFuelTypeValues
import hr.fer.carpulse.ui.components.CarPulseTextField
import hr.fer.carpulse.ui.components.DropdownPicker
import hr.fer.carpulse.ui.components.NavigationCarousel
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.subtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.carImageIds
import hr.fer.carpulse.util.defaultKeyboardActions
import hr.fer.carpulse.viewmodel.OnboardingViewModel

@Composable
fun OnboardingCarInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel,
    navigateToDriverInfoScreen: () -> Unit
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
                text = stringResource(R.string.onboarding_screen_car_info_title),
                style = title,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = stringResource(R.string.onboarding_screen_car_info_subtitle),
                style = subtitle
            )
        }


        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(bottom = 10.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {

            val tapToChangeColorText =
                stringResource(R.string.onboarding_screen_tap_to_change_color)

            Canvas(modifier = Modifier.size(250.dp)) {
                drawIntoCanvas {
                    val textPadding = 15.dp.toPx()
                    val arcHeight = 250.dp.toPx()
                    val arcWidth = 250.dp.toPx()

                    // Path for curved text
                    val path = Path().apply {
                        addArc(0f, textPadding, arcWidth, arcHeight, 180f, 180f)
                    }
                    it.nativeCanvas.drawTextOnPath(
                        tapToChangeColorText,
                        path,
                        0f,
                        0f,
                        Paint().apply {
                            textSize = 14.sp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            IconButton(onClick = {
                if (viewModel.selectedCarImageIndex == carImageIds.indices.last) {
                    viewModel.updateSelectedCarImageIndex(0)
                } else {
                    viewModel.updateSelectedCarImageIndex(viewModel.selectedCarImageIndex + 1)
                }
            }) {
                Image(
                    modifier = Modifier
                        .height(164.dp)
                        .width(169.dp),
                    painter = painterResource(carImageIds[viewModel.selectedCarImageIndex]),
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 43.dp)
        ) {
            Text(
                text = stringResource(R.string.onboarding_screen_vehicle_type),
                style = subtitle
            )
            CarPulseTextField(
                modifier = Modifier.fillMaxWidth(),
                value = driverData.vehicleType,
                onChange = { viewModel.updateVehicleType(it) },
                placeholder = "",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )

            Spacer(modifier = Modifier.height(7.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_fuel_type),
                        style = subtitle
                    )
                    DropdownPicker(
                        values = getFuelTypeValues(),
                        selectedItem = if (driverData.fuelType == "") {
                            // Set default value if the user does not change the initial state
                            viewModel.updateFuelType(getFuelTypeValues().first())
                            getFuelTypeValues().first()
                        } else driverData.fuelType,
                        onOptionSelected = { viewModel.updateFuelType(it) }
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_motor_power),
                        style = subtitle
                    )
                    CarPulseTextField(
                        value = if (driverData.vehicleMotorPower == 0) "" else driverData.vehicleMotorPower.toString(),
                        onChange = { viewModel.updateVehicleMotorPower(it) },
                        placeholder = "",
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                        keyboardActions = keyboardActions
                    )
                }
            }

            Spacer(modifier = Modifier.height(7.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_production_year),
                        style = subtitle
                    )
                    CarPulseTextField(
                        value = if (driverData.vehicleProductionYear == 0) "" else driverData.vehicleProductionYear.toString(),
                        onChange = { viewModel.updateVehicleProductionYear(it) },
                        placeholder = "",
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                        keyboardActions = keyboardActions
                    )
                }

                Spacer(modifier = Modifier.width(30.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.onboarding_screen_start_stop_system),
                        style = subtitle
                    )
                    DropdownPicker(
                        values = arrayOf("Yes", "No"),
                        selectedItem = if (driverData.startStopSystem) "Yes" else "No",
                        onOptionSelected = {
                            if (it == "Yes") {
                                viewModel.updateStartStopSystem(true)
                            } else {
                                viewModel.updateStartStopSystem(false)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            NavigationCarousel(
                carouselCount = 3,
                currentCarouselIndex = 0,
                onPreviousButtonClick = {
                    // Do nothing - not visible!
                },
                onNextButtonClick = navigateToDriverInfoScreen
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}