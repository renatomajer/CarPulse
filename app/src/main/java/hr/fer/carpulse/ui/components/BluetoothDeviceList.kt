package hr.fer.carpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.BluetoothDevice
import hr.fer.carpulse.ui.theme.Teal200
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.mediumPadding

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    connectedDeviceAddress: String?,
    isScanning: Boolean,
    onClickConnect: (BluetoothDevice) -> Unit,
    onClickDisconnect: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = stringResource(id = R.string.paired_devices),
                style = Typography.h2,
                modifier = Modifier.padding(mediumPadding),
                color = Teal200
            )
        }

        items(pairedDevices) { device ->
            // connected device
            if (device.address == connectedDeviceAddress) {
                BluetoothDeviceComponent(
                    onClickConnect = onClickConnect,
                    onClickDisconnect = onClickDisconnect,
                    device = device,
                    isConnectedTo = true
                )

            } else {
                // only paired device
                Text(
                    text = device.name ?: stringResource(id = R.string.no_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickConnect(device) }
                        .padding(mediumPadding)
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.scanned_devices),
                style = Typography.h2,
                modifier = Modifier.padding(mediumPadding),
                color = Teal200
            )
        }

        if (isScanning) {
            item {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }

        items(scannedDevices.filter { scannedDevice ->
            !pairedDevices.map { it.address }.contains(scannedDevice.address)
        }) { device ->
            // scanned device
            BluetoothDeviceComponent(
                onClickConnect = onClickConnect,
                onClickDisconnect = onClickDisconnect,
                device = device,
                isConnectedTo = false
            )
        }
    }
}