package hr.fer.carpulse.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale.Companion.current
import androidx.compose.ui.text.toUpperCase
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.BluetoothDeviceList
import hr.fer.carpulse.ui.components.ScreenTopBar
import hr.fer.carpulse.ui.theme.bigPadding
import hr.fer.carpulse.viewmodel.ConnectScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ConnectScreen(
    applicationContext: Context,
    navController: NavController
) {

    val connectScreenViewModel = getViewModel<ConnectScreenViewModel>()

    val isConnecting by connectScreenViewModel.isConnecting().collectAsState()
    val isConnected by connectScreenViewModel.isConnected().collectAsState()
    val errorMessage by connectScreenViewModel.errorMessage().collectAsState()
    val connectedDeviceAddress by connectScreenViewModel.getConnectedDeviceAddress()
        .collectAsState()

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = isConnected) {

        if (isConnected) {
            Toast.makeText(
                applicationContext,
                "You're connected!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    ScreenTopBar(
        onBackArrowClick = {
            navController.popBackStack()
        }
    ) { paddingValues ->

        when {
            isConnecting -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = stringResource(id = R.string.connecting))
                }
            }

            else -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    val pairedDevices by connectScreenViewModel.getPairedDevices()
                        .collectAsState(initial = emptyList())

                    val scannedDevices by connectScreenViewModel.getScannedDevices()
                        .collectAsState(initial = emptyList())

                    val isScanning by connectScreenViewModel.isScanning().collectAsState()

                    BluetoothDeviceList(
                        pairedDevices = pairedDevices,
                        scannedDevices = scannedDevices,
                        connectedDeviceAddress = connectedDeviceAddress,
                        onClickConnect = connectScreenViewModel::connectToDevice,
                        onClickDisconnect = connectScreenViewModel::disconnectFromDevice,
                        isScanning = isScanning,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = bigPadding),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(onClick = { connectScreenViewModel.startScan() }) {
                            Text(text = stringResource(id = R.string.start_scan).toUpperCase(current))
                        }

                        Button(
                            onClick = { connectScreenViewModel.stopScan() },
                        ) {
                            Text(text = stringResource(id = R.string.stop_scan).toUpperCase(current))
                        }

//                        if (isConnected) {
//                            Button(onClick = connectScreenViewModel::disconnectFromDevice) {
//                                Text(
//                                    text = stringResource(id = R.string.disconnect).toUpperCase(
//                                        current
//                                    )
//                                )
//                            }
//                        }
                    }
                }

            }
        }
    }
}