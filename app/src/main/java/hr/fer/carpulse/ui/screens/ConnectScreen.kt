package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.BluetoothDeviceList
import hr.fer.carpulse.ui.components.ScreenTopBar
import hr.fer.carpulse.viewmodel.ConnectScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ConnectScreen(
    navController: NavController
) {

    val connectScreenViewModel = getViewModel<ConnectScreenViewModel>()

    ScreenTopBar(
        onBackArrowClick = {
            navController.popBackStack()
        }
    ) { paddingValues ->

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
                onClick = { /* TODO */ },
                isScanning = isScanning,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(onClick = { connectScreenViewModel.startScan() }) {
                    Text(text = stringResource(id = R.string.start_scan))
                }

                Button(onClick = { connectScreenViewModel.stopScan() }) {
                    Text(text = stringResource(id = R.string.stop_scan))
                }
            }
        }
    }
}