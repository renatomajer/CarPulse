package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
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
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.driver.Gender
import hr.fer.carpulse.domain.common.driver.getFuelTypeValues
import hr.fer.carpulse.navigation.Screens
import hr.fer.carpulse.ui.components.DataTextField
import hr.fer.carpulse.ui.components.DropdownPicker
import hr.fer.carpulse.ui.components.LabeledRadioButton
import hr.fer.carpulse.ui.theme.Purple500
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.microPadding
import hr.fer.carpulse.util.defaultKeyboardActions
import hr.fer.carpulse.viewmodel.UserDataScreenViewModel
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun UserDataScreen(
    navController: NavController,
    isOnboarding: Boolean
) {

    val focusManager = LocalFocusManager.current
    val keyboardActions = defaultKeyboardActions(focusManager)

    val userDataScreenViewModel = getViewModel<UserDataScreenViewModel>()

    if (!isOnboarding) {
        userDataScreenViewModel.getDriverData()
    }

    val driverData by userDataScreenViewModel.driverData.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(start = microPadding, end = microPadding)
            .fillMaxSize()
    ) {

        if (isOnboarding) {
            item {
                Text(
                    text = stringResource(id = R.string.welcome_message),
                    style = Typography.h2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = microPadding, bottom = microPadding),
                    textAlign = TextAlign.Center
                )
            }

            item {
                Text(text = stringResource(id = R.string.enter_user_data_message))
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Purple500)
                        .width(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.driver_info).uppercase(Locale.getDefault()),
                    modifier = Modifier
                        .width(90.dp)
                        .padding(start = 5.dp, end = 5.dp),
                    color = Purple500,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Purple500)
                        .width(100.dp)
                )
            }
        }

        item {
            DataTextField(
                value = driverData.email,
                placeholder = stringResource(id = R.string.email),
                onChange = {
                    userDataScreenViewModel.updateEmail(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email,
                keyboardActions = keyboardActions,
                readOnly = !isOnboarding
            )
        }

        item {
            DataTextField(
                value = if (driverData.age == 0) "" else driverData.age.toString(),
                placeholder = stringResource(id = R.string.age),
                onChange = {
                    userDataScreenViewModel.updateAge(it)
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            Text(text = stringResource(id = R.string.gender) + ":")
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LabeledRadioButton(
                    selected = driverData.gender == Gender.Male.value,
                    onClick = {
                        userDataScreenViewModel.updateGender(Gender.Male)
                    },
                    text = stringResource(id = R.string.male)
                )
                LabeledRadioButton(
                    selected = driverData.gender == Gender.Female.value,
                    onClick = {
                        userDataScreenViewModel.updateGender(Gender.Female)
                    },
                    text = stringResource(id = R.string.female)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Purple500)
                        .width(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.car_info).uppercase(Locale.getDefault()),
                    modifier = Modifier
                        .width(90.dp)
                        .padding(start = 5.dp, end = 5.dp),
                    color = Purple500,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Purple500)
                        .width(100.dp)
                )
            }
        }

        item {
            DropdownPicker(
                values = getFuelTypeValues(),
                onOptionSelected = {
                    userDataScreenViewModel.updateFuelType(it)
                },
                selectedItem = if (driverData.fuelType == "") getFuelTypeValues().first() else driverData.fuelType
            )
        }

        item {
            DataTextField(
                value = driverData.vehicleType,
                placeholder = stringResource(id = R.string.vehicle_type),
                onChange = {
                    userDataScreenViewModel.updateVehicleType(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.vehicleProductionYear == 0) "" else driverData.vehicleProductionYear.toString(),
                placeholder = stringResource(id = R.string.vehicle_production_year),
                onChange = {
                    userDataScreenViewModel.updateVehicleProductionYear(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.vehicleMotorPower == 0) "" else driverData.vehicleMotorPower.toString(),
                placeholder = stringResource(id = R.string.vehicle_motor_power),
                onChange = {
                    userDataScreenViewModel.updateVehicleMotorPower(it)
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            Text(text = stringResource(id = R.string.start_stop_system) + ":")
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LabeledRadioButton(
                    selected = driverData.startStopSystem,
                    onClick = {
                        userDataScreenViewModel.updateStartStopSystem(true)
                    },
                    text = stringResource(id = R.string.yes)
                )
                LabeledRadioButton(
                    selected = !driverData.startStopSystem,
                    onClick = {
                        userDataScreenViewModel.updateStartStopSystem(false)
                    },
                    text = stringResource(id = R.string.no)
                )
            }
        }

        item {
            DataTextField(
                value = if (driverData.drivingAge == 0) "" else driverData.drivingAge.toString(),
                placeholder = stringResource(id = R.string.driving_age),
                onChange = {
                    userDataScreenViewModel.updateDrivingAge(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.drivingInPrivateVehicle == 0) "" else driverData.drivingInPrivateVehicle.toString(),
                placeholder = stringResource(id = R.string.driving_in_private_vehicle),
                onChange = {
                    userDataScreenViewModel.updateDrivingInPrivateVehicle(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.driverInPrivateVehicle == 0) "" else driverData.driverInPrivateVehicle.toString(),
                placeholder = stringResource(id = R.string.driver_in_private_vehicle),
                onChange = {
                    userDataScreenViewModel.updateDriverInPrivateVehicle(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.fuelConsumptionOptimisation == 0) "" else driverData.fuelConsumptionOptimisation.toString(),
                placeholder = stringResource(id = R.string.fuel_consumption_optimisation),
                onChange = {
                    userDataScreenViewModel.updateFuelConsumptionOptimisation(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (driverData.drivingCrowdedRoads == 0) "" else driverData.drivingCrowdedRoads.toString(),
                placeholder = stringResource(id = R.string.driving_crowded_roads),
                onChange = {
                    userDataScreenViewModel.updateDrivingCrowdedRoads(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = driverData.comfort,
                placeholder = stringResource(id = R.string.comfort),
                onChange = {
                    userDataScreenViewModel.updateComfort(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = driverData.security,
                placeholder = stringResource(id = R.string.security),
                onChange = {
                    userDataScreenViewModel.updateSecurity(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = driverData.sportsStyle,
                placeholder = stringResource(id = R.string.sports_style),
                onChange = {
                    userDataScreenViewModel.updateSportsStyle(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = driverData.fuelEfficiency,
                placeholder = stringResource(id = R.string.fuel_efficiency),
                onChange = {
                    userDataScreenViewModel.updateFuelEfficiency(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = driverData.neutralTraffic,
                placeholder = stringResource(id = R.string.neutral_traffic),
                onChange = {
                    userDataScreenViewModel.updateNeutralTraffic(it)
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }


        item {
            Button(onClick = {
                userDataScreenViewModel.saveDriverData()

                if (isOnboarding) {
                    userDataScreenViewModel.saveOnBoardingState(completed = true)
                }

                navController.popBackStack()

                if (isOnboarding) {
                    navController.navigate(Screens.HomeScreen.route)
                }
            }) {
                Text(text = stringResource(id = R.string.done))
            }
        }

    }
}