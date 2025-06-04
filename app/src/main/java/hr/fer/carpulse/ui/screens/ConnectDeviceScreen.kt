package hr.fer.carpulse.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.BluetoothDevice
import hr.fer.carpulse.ui.components.ScanningButton
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.TealColor
import hr.fer.carpulse.ui.theme.extraLightText
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.viewmodel.ConnectDeviceViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun ConnectDeviceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: ConnectDeviceViewModel = getViewModel()
) {

    val context = LocalContext.current

    val connectingDeviceAddress by viewModel.getConnectingDeviceAddress().collectAsState()
    val isConnected by viewModel.isConnected().collectAsState()
    val errorMessage by viewModel.errorMessage().collectAsState()
    val connectedDeviceAddress by viewModel.getConnectedDeviceAddress()
        .collectAsState()

    val pairedDevices by viewModel.getPairedDevices()
        .collectAsState(initial = emptyList())

    val scannedDevices = viewModel.getScannedDevices()
        .collectAsState(initial = emptyList()).value.filter { scannedDevice ->
            !pairedDevices.map { it.address }.contains(scannedDevice.address)}

    val isScanning by viewModel.isScanning().collectAsState()

    val isBluetoothEnabled = true

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = isConnected) {

        if (isConnected) {
            Toast.makeText(
                context,
                "You're connected!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = modifier
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
                onClick = onNavigateBack
            ) {
                Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(text = stringResource(R.string.connect_device_screen_title), style = title)

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
                .verticalScroll(rememberScrollState())
                .padding(start = 38.dp, end = 26.dp, top = 50.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.connect_device_screen_bluetooth), style = title)

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.connect_device_screen_bluetooth_switch_off),
                    style = extraLightText
                )

                Switch(
                    checked = isBluetoothEnabled,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = TealColor,
                        checkedTrackAlpha = 1f,
                        uncheckedThumbColor = TealColor,
                        uncheckedTrackColor = Color.LightGray
                    )
                )

                Text(
                    text = stringResource(R.string.connect_device_screen_bluetooth_switch_on),
                    style = extraLightText
                )
            }

            Spacer(modifier = Modifier.height(39.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LightGrayColor)
            )

            Spacer(modifier = Modifier.height(23.dp))

            if (!isBluetoothEnabled) {
                Text(
                    stringResource(R.string.connect_device_screen_turn_on_bluetooth_message),
                    style = extraLightText
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.connect_device_screen_scanning),
                        style = title
                    )

                    if (isScanning) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = LightGrayColor
                        )
                    }

                    ScanningButton(
                        onClick = {
                            if (isScanning) {
                                viewModel.stopScan()
                            } else {
                                viewModel.startScan()
                            }
                        },
                        isScanning = isScanning
                    )
                }

                Spacer(modifier = Modifier.height(33.dp))

                Text(
                    text = stringResource(R.string.connect_device_screen_paired_devices),
                    style = title
                )

                Spacer(modifier = Modifier.height(10.dp))

                pairedDevices.forEach { bluetoothDevice ->
                    BluetoothDevice(
                        deviceName = bluetoothDevice.name ?: "Unknown",
                        onConnectClick = { viewModel.connectToDevice(bluetoothDevice) },
                        isConnected = connectedDeviceAddress == bluetoothDevice.address,
                        isConnecting = bluetoothDevice.address == connectingDeviceAddress
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = stringResource(R.string.connect_device_screen_scanned_devices),
                    style = title
                )

                scannedDevices.forEach { bluetoothDevice ->
                    BluetoothDevice(
                        deviceName = bluetoothDevice.name ?: "Unknown",
                        onConnectClick = { viewModel.connectToDevice(bluetoothDevice) },
                        isConnected = connectedDeviceAddress == bluetoothDevice.address,
                        isConnecting = bluetoothDevice.address == connectingDeviceAddress
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

        }
    }

}