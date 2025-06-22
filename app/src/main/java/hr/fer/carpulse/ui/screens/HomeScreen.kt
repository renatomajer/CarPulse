package hr.fer.carpulse.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.ui.components.CircularGauge
import hr.fer.carpulse.ui.components.DataFieldComponent
import hr.fer.carpulse.ui.components.DrawerContent
import hr.fer.carpulse.ui.components.RecordingButton
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.BlueColor
import hr.fer.carpulse.ui.theme.OrangeColor
import hr.fer.carpulse.ui.theme.subtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = getViewModel(),
    navigateToTripReviewScreen: (tripUUID: String) -> Unit,
    navigateToConnectScreen: () -> Unit,
    navigateToDrivingHistoryScreen: () -> Unit,
    navigateToEditProfileScreen: () -> Unit,
    navigateToTalkWithAssistant: () -> Unit,
    navigateToConfigureAssistant: () -> Unit,
    navigateToStatistics: () -> Unit
) {

    val context = LocalContext.current

    val obdReading by viewModel.getOBDReadingDataFlow()
        .collectAsState(initial = OBDReading(timestamp = 0L))

    val isMeasuring by viewModel.getIsMeasuring().collectAsState()
    val errorMessage by viewModel.getErrorMessages().collectAsState()
    val isConnected by viewModel.getIsDeviceConnected().collectAsState()

    val isStartButtonEnabled = isConnected && !isMeasuring
    val isStopButtonEnabled = isConnected && isMeasuring

    val maxSpeed = 200f
    val maxRpm = 5000f

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.retrieveDriverData()
    }

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    ModalDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                driverName = viewModel.driverName ?: "",
                vehicleType = viewModel.vehicleType ?: "",
                avatarBgColorIndex = viewModel.avatarColorIndex ?: 0,
                onCloseClick = { coroutineScope.launch { drawerState.close() } },
                onEditProfileClick = navigateToEditProfileScreen,
                onDrivingHistoryClick = navigateToDrivingHistoryScreen,
                onStatisticsClick = navigateToStatistics,
                onTalkWithAssistantClick = navigateToTalkWithAssistant,
                onConfigureAssistantClick = navigateToConfigureAssistant,
                onScanAndConnectClick = navigateToConnectScreen,
                uploadToServer = !viewModel.storeDataLocally,
                onUploadToServerChange = { viewModel.saveLocalStorageState(uploadToServer = it) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppBackgroundColor)
        ) {
            Spacer(modifier = Modifier.height(65.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(100)
                        ),
                    onClick = { coroutineScope.launch { drawerState.open() } }
                ) {
                    Icon(Icons.Outlined.Menu, contentDescription = null)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(text = stringResource(R.string.home_screen_title), style = title)

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 30.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 47.dp, end = 31.dp, top = 50.dp)
            ) {
                Text(text = stringResource(R.string.home_screen_obd_device_status), style = title)
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = if (isConnected) painterResource(R.drawable.ic_obd_connected)
                        else painterResource(R.drawable.ic_obd_disconnected),
                        contentDescription = null
                    )

                    Text(
                        text = if (isConnected) stringResource(R.string.home_screen_obd_device_connected)
                        else stringResource(R.string.home_screen_obd_device_disconnected),
                        style = subtitle
                    )

                    IconButton(
                        modifier = Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(100)
                        ), onClick = navigateToConnectScreen
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                Text(text = stringResource(R.string.home_screen_record_trip_title), style = title)
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RecordingButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.home_screen_record_trip_start),
                        enabled = isStartButtonEnabled,
                        imageRes = if (isStartButtonEnabled) R.drawable.ic_start_enabled
                        else R.drawable.ic_start_disabled,
                        onClick = { viewModel.startMeasuring() }
                    )

                    Spacer(modifier = Modifier.width(33.dp))

                    RecordingButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.home_screen_record_trip_stop),
                        enabled = isStopButtonEnabled,
                        imageRes = if (isStopButtonEnabled) R.drawable.ic_stop_enabled
                        else R.drawable.ic_stop_disabled,
                        onClick = {
                            viewModel.stopMeasuring()
                            navigateToTripReviewScreen(viewModel.tripUUID.toString())
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(text = stringResource(R.string.home_screen_data_import_status), style = title)
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(86.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(13.dp)
                        )
                        .padding(start = 13.dp, top = 12.dp, end = 18.dp, bottom = 8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_timestamp),
                        data = if (isMeasuring)
                            obdReading.timestamp.toString()
                        else
                            stringResource(R.string.home_screen_data_no_data_label)
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_rpm),
                        data = obdReading.rpm
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_speed),
                        data = obdReading.speed
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_relative_throttle_position),
                        data = obdReading.relativeThrottlePosition
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_absolute_throttle_position_b),
                        data = obdReading.absoluteThrottlePositionB
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_throttle_position),
                        data = obdReading.throttlePosition
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_accelerator_pedal_position_e),
                        data = obdReading.acceleratorPedalPositionE
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_accelerator_pedal_position_d),
                        data = obdReading.acceleratorPedalPositionD
                    )
                    DataFieldComponent(
                        dataTypeDescription = stringResource(R.string.home_screen_data_engine_load),
                        data = obdReading.engineLoad
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 7.dp),
                            text = stringResource(R.string.home_screen_data_speed),
                            style = title
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularGauge(
                                value = if (obdReading.speed == OBDReading.NO_DATA) 0f
                                else obdReading.speed.removeSuffix("km/h").toFloat(),
                                maxValue = maxSpeed,
                                label = stringResource(R.string.home_screen_gauge_speed_label),
                                height = 150,
                                width = 150,
                                indicatorColor = BlueColor
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 10.dp),
                            text = stringResource(R.string.home_screen_data_rpm),
                            style = title
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularGauge(
                                value = if (obdReading.rpm == OBDReading.NO_DATA) 0f
                                else obdReading.rpm.removeSuffix("RPM").toFloat(),
                                maxValue = maxRpm,
                                label = stringResource(R.string.home_screen_gauge_rpm_label),
                                height = 150,
                                width = 150,
                                indicatorColor = OrangeColor
                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.home_screen_talk_with_ai_assistant),
                        style = title
                    )

                    IconButton(
                        modifier = Modifier.size(70.dp),
                        onClick = navigateToTalkWithAssistant
                    ) {
                        Image(
                            modifier = Modifier.size(70.dp),
                            painter = painterResource(R.drawable.ic_ai_assistant),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
