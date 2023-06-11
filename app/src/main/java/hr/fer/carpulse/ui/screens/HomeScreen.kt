package hr.fer.carpulse.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.navigation.Screens
import hr.fer.carpulse.ui.components.OBDReadingDataComponent
import hr.fer.carpulse.ui.theme.*
import hr.fer.carpulse.viewmodel.HomeScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    applicationContext: Context,
    navController: NavController
) {
    val homeScreenViewModel = getViewModel<HomeScreenViewModel>()

    val isMeasuring by homeScreenViewModel.getIsMeasuring().collectAsState()
    val errorMessage by homeScreenViewModel.getErrorMessages().collectAsState()
    val isConnected by homeScreenViewModel.getIsDeviceConnected().collectAsState()

    val obdReading by homeScreenViewModel.getOBDReadingDataFlow()
        .collectAsState(initial = OBDReading(timestamp = 0L))

    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(
                                id = R.string.menu
                            )
                        )
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.ConnectScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.connect_device))
                        }

                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.TripsScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.show_trips))
                        }

                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.SettingsScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.settings))
                        }
                    }
                }
            )
        }

    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.obd_device_status)
                )

                Text(
                    text = if (isConnected) stringResource(
                        id = R.string.connected
                    ) else stringResource(id = R.string.disconnected),
                    color = if (isConnected) Purple200 else MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }

            OBDReadingDataComponent(obdReading)

            Row(
                modifier = Modifier.padding(
                    top = measurementButtonsTopBottomPadding,
                    bottom = measurementButtonsTopBottomPadding
                ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .width(measurementButtonWidth)
                        .height(measurementButtonHeight),
                    enabled = !isMeasuring,
                    onClick = {
                        homeScreenViewModel.startMeasuring()
                    }) {
                    Text(
                        text = stringResource(id = R.string.start_measurement).toUpperCase(Locale.current),
                        textAlign = TextAlign.Center
                    )
                }

                // TODO: uncomment the line below
                Button(
                    modifier = Modifier
                        .width(measurementButtonWidth)
                        .height(measurementButtonHeight),
                    enabled = isMeasuring,
                    onClick = {
                        homeScreenViewModel.stopMeasuring()
                        navController.navigate(Screens.TripReviewScreen.route + "/${homeScreenViewModel.tripUUID}")
                    }) {
                    Text(
                        text = stringResource(id = R.string.stop_measurement).toUpperCase(Locale.current),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}